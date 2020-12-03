package com.common.platform.sys.auth;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.common.platform.auth.context.LoginContextHolder;
import com.common.platform.auth.exception.AuthException;
import com.common.platform.auth.exception.enums.AuthExceptionEnum;
import com.common.platform.auth.jwt.payload.JwtPayLoad;
import com.common.platform.auth.jwt.payload.JwtTokenUtil;
import com.common.platform.auth.pojo.LoginUser;
import com.common.platform.auth.service.AuthService;
import com.common.platform.base.config.context.HttpContext;
import com.common.platform.base.config.context.SpringContextHolder;
import com.common.platform.base.utils.CoreUtil;
import com.common.platform.sys.auth.cache.SessionManager;
import com.common.platform.sys.factory.ConstantFactory;
import com.common.platform.sys.factory.UserFactory;
import com.common.platform.sys.listener.ConfigListener;
import com.common.platform.sys.log.LogManager;
import com.common.platform.sys.log.factory.LogTaskFactory;
import com.common.platform.sys.modular.system.entity.User;
import com.common.platform.sys.modular.system.mapper.MenuMapper;
import com.common.platform.sys.modular.system.mapper.UserMapper;
import com.common.platform.sys.modular.system.service.DictService;
import com.common.platform.sys.state.ManagerStatus;
import com.common.platform.sys.util.SaltUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.common.platform.base.config.context.HttpContext.getIp;
import static com.common.platform.base.consts.ConstantsContext.*;

@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private DictService dictService;

    @Autowired
    private SessionManager sessionManager;

    public static AuthService me(){
        return SpringContextHolder.getBean(AuthService.class);
    }

    @Override
    public String login(String username, String password) {
        User user = this.userMapper.getByAccount(username);
        if(user == null){
            throw new AuthException(AuthExceptionEnum.USERNAME_PWD_ERROR);
        }
        String requestMd5 = SaltUtil.md5Encrypt(password,user.getSalt());
        String dbMd5 = user.getPassword();

        if(dbMd5==null || !dbMd5.equalsIgnoreCase(requestMd5)){
            throw new AuthException(AuthExceptionEnum.USERNAME_PWD_ERROR);
        }
        return login(username);
    }

    @Override
    public String login(String username) {
        User user = this.userMapper.getByAccount(username);
        if(user == null){
            throw new AuthException(AuthExceptionEnum.USERNAME_PWD_ERROR);
        }
        if(!user.getStatus().equals(ManagerStatus.OK.getCode())){
            throw new AuthException(AuthExceptionEnum.ACCOUNT_FREEZE_ERROR);
        }
        //记录日志
        LogManager.me().executeLog(LogTaskFactory.loginLog(user.getUserId(),getIp()));

        //JWT PayLoad
        JwtPayLoad jwtPayLoad = new JwtPayLoad(user.getUserId(),user.getAccount(),"xxxx");
        //创建Token
        String token = JwtTokenUtil.generateToken(jwtPayLoad);

        //创建当前登录的会话
        sessionManager.createSession(token,user(username));

        //创建cookie
        addLoginCookie(token);

        return token;
    }

    @Override
    public void addLoginCookie(String token) {
        Cookie authorization = new Cookie(getTokenHeaderName(),token);
        authorization.setMaxAge(getJwtSecretExpireSec().intValue());
        authorization.setHttpOnly(true);
        authorization.setPath("/");
        HttpServletResponse response = HttpContext.getResponse();
        response.addCookie(authorization);
    }

    @Override
    public void logout() {
        String token = LoginContextHolder.getContext().getToken();
        logout(token);
    }

    @Override
    public void logout(String token) {
        //记录日志
        LogManager.me().executeLog(LogTaskFactory.exitLog(LoginContextHolder.getContext().getUserId(),getIp()));

        Cookie[] cookies = HttpContext.getRequest().getCookies();
        for (Cookie cookie:cookies){
            String tokenHeader = getTokenHeaderName();
            if(tokenHeader.equalsIgnoreCase(cookie.getName())){
                Cookie temp = new Cookie(cookie.getName(),"");
                temp.setMaxAge(0);
                HttpContext.getResponse().addCookie(temp);
            }
        }

        sessionManager.removeSession(token);
    }

    @Override
    public LoginUser user(String account) {
        User user = this.userMapper.getByAccount(account);
        LoginUser loginUser = UserFactory.createLoginUser(user);
        Long[] roleArray = Convert.toLongArray(user.getRoleId());
        if(roleArray==null || roleArray.length<=0){
            return loginUser;
        }
        List<Long> roleList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        List<String> roleTipList = new ArrayList<>();
        for (Long roleId : roleArray){
            roleList.add(roleId);
            roleNameList.add(ConstantFactory.me().getSingleRoleName(roleId));
            roleTipList.add(ConstantFactory.me().getSingleRoleTip(roleId));
        }
        loginUser.setRoleList(roleList);
        loginUser.setRoleNames(roleNameList);
        loginUser.setRoleTips(roleTipList);

        List<String> systemTypes = this.menuMapper.getMenusTypesByRoleIds(roleList);
        List<Map<String,Object>> dictsByCodes = this.dictService.getDictsByCodes(systemTypes);
        loginUser.setSystemTypes(dictsByCodes);

        Set<String> permissionSet = new HashSet<>();
        for(Long roleId : roleList){
            List<String> permissions = this.findPermissionByRoleId(roleId);
            if(permissions!=null){
                for (String permission:permissions){
                    if(CoreUtil.isNotEmpty(permission)){
                        permissionSet.add(permission);
                    }
                }
            }
        }
        loginUser.setPermissions(permissionSet);
        return loginUser;
    }

    @Override
    public List<String> findPermissionByRoleId(Long roleId) {
        return this.menuMapper.getResUrlsByRoleId(roleId);
    }

    @Override
    public boolean check(String[] roleName) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        if(user==null){
            return false;
        }
        ArrayList<String> objects = CollectionUtil.newArrayList(roleName);
        String join = CollectionUtil.join(objects,",");
        if(LoginContextHolder.getContext().hasAnyRoles(join)){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkAll() {
        HttpServletRequest request = HttpContext.getRequest();
        LoginUser loginUser = LoginContextHolder.getContext().getUser();
        if(loginUser==null){
            return false;
        }
        String requestURI = request.getRequestURI()
                .replaceFirst(ConfigListener.getConf().get("contextPath"),"");
        String[] str = requestURI.split("/");
        if(str.length>3){
            requestURI = "/" + str[1] +"/" +str[2];
        }
        if(LoginContextHolder.getContext().hasPermission(requestURI)){
            return true;
        }
        return false;
    }
}
