package com.common.platform.sys.modular.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.platform.auth.annotation.Permission;
import com.common.platform.auth.context.LoginContextHolder;
import com.common.platform.base.config.database.DataScope;
import com.common.platform.base.config.sys.Const;
import com.common.platform.base.consts.ConstantsContext;
import com.common.platform.base.exception.BizExceptionEnum;
import com.common.platform.base.exception.ServiceException;
import com.common.platform.base.log.BussinessLog;
import com.common.platform.base.page.LayuiPageInfo;
import com.common.platform.base.page.factory.LayuiPageFactory;
import com.common.platform.base.utils.CoreUtil;
import com.common.platform.sys.base.controller.response.ResponseData;
import com.common.platform.sys.dictmap.UserDict;
import com.common.platform.sys.exception.RequestEmptyException;
import com.common.platform.sys.log.LogObjectHolder;
import com.common.platform.sys.modular.system.entity.User;
import com.common.platform.sys.modular.system.model.UserDto;
import com.common.platform.sys.modular.system.service.UserService;
import com.common.platform.sys.modular.system.wrapper.UserWrapper;
import com.common.platform.sys.state.ManagerStatus;
import com.common.platform.sys.util.SaltUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.common.platform.sys.base.controller.BaseController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 */
@Controller
@RequestMapping("/mgr")
public class UserMgrController extends BaseController {

    private static String PREFIX = "/modular/system/user/";

    @Autowired
    private UserService userService;

    /**
     * 跳转到人员首页
     */
    @RequestMapping("")
    public String index(){
        return PREFIX + "user.html";
    }

    /**
     * 跳转到人员添加页面
     */
    @RequestMapping("/user_add")
    public String addView(){
        return PREFIX + "user_add.html";
    }

    /**
     * 跳转到角色分配页面
     */
    @Permission
    @RequestMapping("/role_assign")
    public String roleAssign(@RequestParam("userId") Long userId, Model model){
        if(CoreUtil.isEmpty(userId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        model.addAttribute("userId",userId);
        return PREFIX + "user_roleassign.html";
    }

    /**
     * 跳转到人员编辑页面
     */
    @Permission
    @RequestMapping("/user_edit")
    public String userEdit(@RequestParam("userId") Long userId){
        if(CoreUtil.isEmpty(userId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = this.userService.getById(userId);
        LogObjectHolder.me().set(user);
        return PREFIX + "user_edit.html";
    }

    /**
     * 获取用户详情
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public ResponseData getUserInfo(@RequestParam("userId") Long userId){
        if(CoreUtil.isEmpty(userId)){
            throw new RequestEmptyException();
        }
        this.userService.assertAuth(userId);
        Map<String,Object> user = this.userService.getUserInfo(userId);
        return ResponseData.success(user);
    }

    /**
     * 修改当前用户的密码
     */
    @RequestMapping("/changePwd")
    @ResponseBody
    public ResponseData changePwd(@RequestParam("oldPassword") String oldPassword,
                                  @RequestParam("newPassword") String newPassword){
        if(CoreUtil.isOneEmpty(oldPassword,newPassword)){
            throw new RequestEmptyException();
        }
        this.userService.changePwd(oldPassword,newPassword);
        return SUCCESS_TIP;
    }

    /**
     * 查询人员列表
     */
    @Permission
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(value = "name",required = false) String name,
                       @RequestParam(value = "timeLImt",required = false) String timeLimit,
                       @RequestParam(value = "deptId",required = false) Long deptId){
        String beginTime = "";
        String endTime = "";

        if(CoreUtil.isNotEmpty(timeLimit)){
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }

        if(LoginContextHolder.getContext().isAdmin()){
            Page<Map<String,Object>> users = this.userService.selectUsers(null,name,beginTime,endTime,deptId);
            Page wrapped = new UserWrapper(users).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        }else{
            DataScope dataScope = new DataScope(LoginContextHolder.getContext().getDeptDataScope());
            Page<Map<String,Object>> users = this.userService.selectUsers(dataScope,name,beginTime,endTime,deptId);
            Page wrapped = new UserWrapper(users).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        }
    }

    /**
     * 添加人员
     */
    @Permission(Const.ADMIN_NAME)
    @BussinessLog(value = "添加人员",key = "account", dict = UserDict.class)
    @RequestMapping("/add")
    @ResponseBody
    public ResponseData add(UserDto user){
        this.userService.addUser(user);
        return SUCCESS_TIP;
    }

    /**
     * 修改人员
     */
    @BussinessLog(value = "修改人员",key = "account", dict = UserDict.class)
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseData edit(UserDto user){
        this.userService.editUser(user);
        return SUCCESS_TIP;
    }

    /**
     * 删除人员
     */
    @Permission
    @BussinessLog(value = "删除人员",key = "account", dict = UserDict.class)
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(@RequestParam("userId") Long userId){
        if(CoreUtil.isEmpty(userId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.userService.deleteUser(userId);
        return SUCCESS_TIP;
    }

    /**
     * 查看人员详情
     */
    @RequestMapping("/view/{userId}")
    @ResponseBody
    public User view(@PathVariable("userId") Long userId){
        if(CoreUtil.isEmpty(userId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.userService.assertAuth(userId);
        return this.userService.getById(userId);
    }

    /**
     * 重置密码
     */
    @Permission(Const.ADMIN_NAME)
    @BussinessLog(value = "重置人员密码",key = "userId", dict = UserDict.class)
    @RequestMapping("/reset")
    @ResponseBody
    public ResponseData reset(@RequestParam("userId") Long userId){
        if(CoreUtil.isEmpty(userId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.userService.assertAuth(userId);
        User user = this.userService.getById(userId);
        user.setSalt(SaltUtil.getRandomSalt());
        user.setPassword(SaltUtil.md5Encrypt(ConstantsContext.getDefaultPassword(),user.getSalt()));
        this.userService.updateById(user);
        return SUCCESS_TIP;
    }

    /**
     * 冻结人员
     */
    @Permission(Const.ADMIN_NAME)
    @BussinessLog(value = "冻结人员",key = "userId", dict = UserDict.class)
    @RequestMapping("/freeze")
    @ResponseBody
    public ResponseData freeze(@RequestParam("userId") Long userId){
        if(CoreUtil.isEmpty(userId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        if(userId.equals(Const.ADMIN_ID)){
            throw new ServiceException(BizExceptionEnum.CANT_FREEZE_ADMIN);
        }
        this.userService.assertAuth(userId);
        this.userService.setStatus(userId, ManagerStatus.FREEZED.getCode());

        return SUCCESS_TIP;
    }

    /**
     * 解除冻结人员
     */
    @Permission(Const.ADMIN_NAME)
    @BussinessLog(value = "解除冻结人员",key = "userId", dict = UserDict.class)
    @RequestMapping("/unfreeze")
    @ResponseBody
    public ResponseData unfreeze(@RequestParam("userId") Long userId){
        if(CoreUtil.isEmpty(userId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        this.userService.assertAuth(userId);
        this.userService.setStatus(userId, ManagerStatus.OK.getCode());

        return SUCCESS_TIP;
    }

    /**
     * 分配角色
     */
    @Permission(Const.ADMIN_NAME)
    @BussinessLog(value = "分配角色",key = "userId,roleIds", dict = UserDict.class)
    @RequestMapping("/setRole")
    @ResponseBody
    public ResponseData setRole(@RequestParam("userId") Long userId,
                                @RequestParam("roleIds") String roleIds){
        if(CoreUtil.isOneEmpty(userId,roleIds)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        if(userId.equals(Const.ADMIN_ID)){
            throw new ServiceException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        this.userService.assertAuth(userId);
        this.userService.setRoles(userId, roleIds);

        return SUCCESS_TIP;
    }

    /**
     * 上传图片
     */
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture){
        String pictureName = UUID.randomUUID().toString() + "." + CoreUtil.getFileSuffix(picture.getOriginalFilename());
        try{
            String fileSavePath = ConstantsContext.getFileUploadPath();
            picture.transferTo(new File(fileSavePath+pictureName));
        }catch (Exception e){
            throw new ServiceException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return pictureName;
    }

    /**
     * 选择办理人
     */
    @RequestMapping("/listUserAndRoleExpectAdmin")
    @ResponseBody
    public LayuiPageInfo listUserAndRoleExpectAdmin(){
        Page pageContext = LayuiPageFactory.defaultPage();
        IPage page = this.userService.listUserAndRoleExpectAdmin(pageContext);
        return LayuiPageFactory.createPageInfo(page);
    }

}

