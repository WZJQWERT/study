package com.common.platform.sys.modular.system.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.platform.auth.annotation.Permission;
import com.common.platform.base.config.sys.Const;
import com.common.platform.base.exception.BizExceptionEnum;
import com.common.platform.base.exception.ServiceException;
import com.common.platform.base.log.BussinessLog;
import com.common.platform.base.page.LayuiPageInfo;
import com.common.platform.base.page.factory.LayuiPageFactory;
import com.common.platform.base.tree.node.ZTreeNode;
import com.common.platform.base.utils.CoreUtil;
import com.common.platform.sys.base.controller.response.ResponseData;
import com.common.platform.sys.dictmap.DeleteDict;
import com.common.platform.sys.dictmap.RoleDict;
import com.common.platform.sys.factory.ConstantFactory;
import com.common.platform.sys.log.LogObjectHolder;
import com.common.platform.sys.modular.system.entity.Role;
import com.common.platform.sys.modular.system.entity.User;
import com.common.platform.sys.modular.system.model.RoleDto;
import com.common.platform.sys.modular.system.service.RoleService;
import com.common.platform.sys.modular.system.service.UserService;
import com.common.platform.sys.modular.system.wrapper.RoleWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.common.platform.sys.base.controller.BaseController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private static String PREFIX = "/modular/system/role";

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    /**
     * 跳转到角色的首页
     */
    @RequestMapping("")
    public String index(){
        return PREFIX + "/role.html";
    }

    /**
     * 跳转到添加角色页面
     */
    @RequestMapping("/role_add")
    public String roleAdd(){
        return PREFIX + "/role_add.html";
    }

    /**
     * 跳转到编辑角色页面
     */
    @Permission
    @RequestMapping("/role_edit")
    public String roleEdit(@RequestParam("roleId") Long roleId){
        if(CoreUtil.isEmpty(roleId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = this.roleService.getById(roleId);
        LogObjectHolder.me().set(role);
        return PREFIX + "/role_edit.html";
    }

    /**
     * 跳转到权限分配
     */
    @Permission
    @RequestMapping("/role_assign/{roleId}")
    public String roleAssign(@PathVariable("roleId") Long roleId, Model model){
        if(CoreUtil.isEmpty(roleId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        model.addAttribute("roleId",roleId);
        return PREFIX + "/role_assign.html";
    }

    /**
     * 获取角色列表
     */
    @Permission
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(value = "roleName",required = false) String roleName){
        Page<Map<String,Object>> roles = this.roleService.selectRoles(roleName);
        Page<Map<String,Object>> wrap = new RoleWrapper(roles).wrap();
        return LayuiPageFactory.createPageInfo(wrap);
    }

    /**
     * 新增接口
     */
    @Permission(Const.ADMIN_NAME)
    @BussinessLog(value = "新增角色",key = "name",dict = RoleDict.class)
    @RequestMapping("/add")
    @ResponseBody
    public ResponseData add(Role role){
        this.roleService.addRole(role);
        return SUCCESS_TIP;
    }

    /**
     * 编辑接口
     */
    @Permission(Const.ADMIN_NAME)
    @BussinessLog(value = "编辑角色",key = "name",dict = RoleDict.class)
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseData edit(RoleDto role){
        this.roleService.editRole(role);
        return SUCCESS_TIP;
    }

    /**
     * 删除接口
     */
    @Permission(Const.ADMIN_NAME)
    @BussinessLog(value = "删除角色",key = "roleId",dict = DeleteDict.class)
    @RequestMapping("/remove")
    @ResponseBody
    public ResponseData remove(@RequestParam("roleId") Long roleId){
        LogObjectHolder.me().set(ConstantFactory.me().getSingleRoleName(roleId));

        this.roleService.delRoleById(roleId);
        return SUCCESS_TIP;
    }

    /**
     * 查看角色接口
     */
    @RequestMapping("/view/{roleId}")
    @ResponseBody
    public ResponseData view(@PathVariable("roleId") Long roleId){
        if(CoreUtil.isEmpty(roleId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = this.roleService.getById(roleId);
        Map<String,Object> roleMap = BeanUtil.beanToMap(role);
        Long pid = role.getPid();
        String pName = ConstantFactory.me().getSingleRoleName(pid);
        roleMap.put("pName",pName);
        return ResponseData.success(roleMap);
    }

    /**
     * 分配权限
     */
    @Permission(Const.ADMIN_NAME)
    @BussinessLog(value = "分配权限",key = "roleId,ids",dict = RoleDict.class)
    @RequestMapping("/setAuthority")
    @ResponseBody
    public ResponseData setAuthority(@RequestParam("roleId") Long roleId,
                                     @RequestParam("ids") String ids){
        if(CoreUtil.isOneEmpty(roleId,ids) ){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.roleService.setAuthority(roleId,ids);
        return SUCCESS_TIP;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping("/roleTreeList")
    @ResponseBody
    public List<ZTreeNode> roleTreeList(){
        List<ZTreeNode> roleTreeList = this.roleService.roleTreeList();
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }

    /**
     * 获取角色列表，通过用户id
     */
    @RequestMapping("/roleTreeListByUserId/{userId}")
    @ResponseBody
    public List<ZTreeNode> roleTreeListByUserId(@PathVariable("userId") Long userId){
        User theUser = this.userService.getById(userId);
        String roleId = theUser.getRoleId();
        if(CoreUtil.isEmpty(roleId)){
            return this.roleService.roleTreeList();
        }else{
            String[] strArray = roleId.split(",");
            Long[] longArray = Convert.toLongArray(strArray);
            return this.roleService.roleTreeListByRoleId(longArray);
        }
    }

    /**
     * 选择角色
     */
    @RequestMapping("/listRole")
    @ResponseBody
    public LayuiPageInfo listRole(@RequestParam(value = "name",required = false) String name){
        IPage page = this.roleService.listRole(name);
        return LayuiPageFactory.createPageInfo(page);
    }

}

