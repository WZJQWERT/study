package com.common.platform.sys.modular.system.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.platform.auth.context.LoginContextHolder;
import com.common.platform.auth.pojo.LoginUser;
import com.common.platform.base.config.database.DataScope;
import com.common.platform.base.config.sys.Const;
import com.common.platform.base.exception.BizExceptionEnum;
import com.common.platform.base.exception.ServiceException;
import com.common.platform.base.page.factory.LayuiPageFactory;
import com.common.platform.base.tree.node.MenuNode;
import com.common.platform.base.utils.CoreUtil;
import com.common.platform.sys.factory.ConstantFactory;
import com.common.platform.sys.factory.UserFactory;
import com.common.platform.sys.modular.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.common.platform.sys.modular.system.entity.UserPos;
import com.common.platform.sys.modular.system.mapper.UserMapper;
import com.common.platform.sys.modular.system.model.UserDto;
import com.common.platform.sys.state.ManagerStatus;
import com.common.platform.sys.util.DefaultImages;
import com.common.platform.sys.util.SaltUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 */
@Service
public class UserService extends ServiceImpl<UserMapper,User> {

    @Autowired
    private UserPosService userPosService;

    @Autowired
    private MenuService menuService;

    /**
     * 新增人员
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUser(UserDto userDto){
        User theUser = this.baseMapper.getByAccount(userDto.getAccount());
        if(theUser!=null){
            throw new ServiceException(BizExceptionEnum.USER_ALREADY_REG);
        }
        String salt = SaltUtil.getRandomSalt();
        String password = SaltUtil.md5Encrypt(userDto.getPassword(),salt);

        User newUser = UserFactory.createUser(userDto,password,salt);
        this.save(newUser);

        addPosition(userDto.getPosition(),newUser.getUserId());
    }

    /**
     *修改用户
     */
    @Transactional(rollbackFor = Exception.class)
    public void editUser(UserDto userDto){
        User oldUser = this.getById(userDto.getUserId());
        if(LoginContextHolder.getContext().hasRole(Const.ADMIN_NAME)){
            this.updateById(UserFactory.editUser(userDto,oldUser));
        }else{
            this.assertAuth(userDto.getUserId());
            LoginUser loginUser = LoginContextHolder.getContext().getUser();
            if(loginUser.getId().equals(userDto.getUserId())){
                this.updateById(UserFactory.editUser(userDto,oldUser));
            }else{
                throw new ServiceException(BizExceptionEnum.NO_PERMITION);
            }
        }

        this.userPosService.remove(new QueryWrapper<UserPos>().eq("user_id",userDto.getUserId()));
        addPosition(userDto.getPosition(),userDto.getUserId());
    }

    /**
     * 删除用户
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userId){
        if(userId.equals(Const.ADMIN_ID)){
            throw new ServiceException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        this.assertAuth(userId);
        this.setStatus(userId, ManagerStatus.DELETED.getCode());

        this.userPosService.remove(new QueryWrapper<UserPos>().eq("user_id",userId));
    }

    /**
     * 修改用户状态
     */
    public int setStatus(Long userId,String status){
        return this.baseMapper.setStatus(userId,status);
    }

    /**
     * 修改密码
     */
    public void changePwd(String oldPassword,String newPassword){
        Long userId =LoginContextHolder.getContext().getUserId();
        User user = this.getById(userId);
        String oldMd5 = SaltUtil.md5Encrypt(oldPassword,user.getSalt());
        if(user.getPassword().equals(oldMd5)){
            String newMd5 = SaltUtil.md5Encrypt(newPassword,user.getSalt());
            user.setPassword(newMd5);
            this.updateById(user);
        }else{
            throw new ServiceException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
        }
    }

    /**
     * 根据条件查询用户列表
     */
    public Page<Map<String,Object>> selectUsers(DataScope dataScope,String name,String beginTime,String endTime,Long deptId){
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.selectUsers(page,dataScope,name,beginTime,endTime,deptId);
    }

    /**
     * 设置用户的角色
     */
    public int setRoles(Long userId,String roleIds){
        return this.baseMapper.setRoles(userId,roleIds);
    }

    /**
     * 通过账号获取用户
     */
    public User getByAccount(String account){
        return this.baseMapper.getByAccount(account);
    }

    /**
     * 获取用户的级别信息
     */
    public Map<String,Object> getUserInfo(Long userId){
        User user = this.getById(userId);
        Map<String,Object> map = UserFactory.removeUnSafeFields(user);
        HashMap<String,Object> hashMap = CollectionUtil.newHashMap();
        hashMap.putAll(map);
        hashMap.put("roleName", ConstantFactory.me().getRoleName(user.getRoleId()));
        hashMap.put("deptName",ConstantFactory.me().getDeptName(user.getDeptId()));
        return hashMap;
    }

    /**
     * 获取用户首页信息
     */
    public Map<String,Object> getUserIndexInfo(){
        LoginUser user = LoginContextHolder.getContext().getUser();
        List<Long> roleList = user.getRoleList();
        if(roleList==null || roleList.size()<=0){
            return null;
        }
        List<Map<String,Object>> menus = this.getUserMenuNodes(roleList);
        HashMap<String,Object> result = new HashMap<>();
        result.put("menus",menus);
        result.put("avatar", DefaultImages.defaultAvatarUrl());
        result.put("name",user.getName());
        return result;
    }

    /**
     * 选择办理人
     */
    public IPage listUserAndRoleExpectAdmin(Page pageContext){
        return this.baseMapper.listUserAndRoleExpectAdmin(pageContext);
    }

    public List<Map<String,Object>> getUserMenuNodes(List<Long> roleList){
        if(roleList==null || roleList.size()<=0){
            return new ArrayList<>();
        }else{
            List<MenuNode> menus = this.menuService.getMenusByRoleIds(roleList);
            ArrayList<Map<String,Object>> lists = new ArrayList<>();
            List<Map<String,Object>> systemTypes = LoginContextHolder.getContext().getUser().getSystemTypes();
            for (Map<String,Object> systemType: systemTypes){
                String systemCode = (String) systemType.get("code");
                ArrayList<MenuNode> originSystemTypeMenus = new ArrayList<>();
                for (MenuNode node:menus){
                    if(node.getSystemType().equals(systemCode)){
                        originSystemTypeMenus.add(node);
                    }
                }

                HashMap<String,Object> map = new HashMap<>();
                List<MenuNode> treeSystemTypeMenus = MenuNode.buildTitle(originSystemTypeMenus);
                map.put("systemType",systemCode);
                map.put("menus",treeSystemTypeMenus);
                lists.add(map);
            }
            return lists;
        }
    }

    public void assertAuth(Long userId){
        if(LoginContextHolder.getContext().isAdmin()){
            return;
        }
        List<Long> deptDataScope = LoginContextHolder.getContext().getDeptDataScope();
        User user = this.getById(userId);
        Long deptId = user.getDeptId();
        if(deptDataScope.contains(deptId)){
            return;
        }else{
            throw new ServiceException(BizExceptionEnum.NO_PERMITION);
        }
    }

    private void addPosition(String positions, Long userId){
        if(CoreUtil.isNotEmpty(positions)){
            String[] position = positions.split(",");
            for (String item:position){
                UserPos entity = new UserPos();
                entity.setUserId(userId);
                entity.setPosId(Long.parseLong(item));
                this.userPosService.save(entity);
            }
        }
    }
}
