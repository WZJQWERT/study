package com.common.platform.sys.modular.system.controller;


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
import com.common.platform.sys.dictmap.MenuDict;
import com.common.platform.sys.factory.ConstantFactory;
import com.common.platform.sys.log.LogObjectHolder;
import com.common.platform.sys.modular.system.entity.Menu;
import com.common.platform.sys.modular.system.model.MenuDto;
import com.common.platform.sys.modular.system.service.MenuService;
import com.common.platform.sys.modular.system.wrapper.MenuWrapper;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 菜单表 前端控制器
 * </p>
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private static String PREFIX = "/modular/system/menu/";

    @Autowired
    private MenuService menuService;

    /**
     * 跳转到菜单首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "menu.html";
    }

    /**
     * 跳转到菜单添加页面
     */
    @RequestMapping("/menu_add")
    public String menuAdd() {
        return PREFIX + "menu_add.html";
    }

    /**
     * 跳转到菜单编辑页面
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping("/menu_edit")
    public String menuEdit(@RequestParam("menuId") Long menuId) {
        if (CoreUtil.isEmpty(menuId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Menu menu = this.menuService.getById(menuId);
        LogObjectHolder.me().set(menu);
        return PREFIX + "menu_edit.html";
    }

    /**
     * 新增接口
     */
    @Permission(Const.ADMIN_NAME)
    @BussinessLog(value = "新增菜单", key = "name", dict = MenuDict.class)
    @RequestMapping("/add")
    @ResponseBody
    public ResponseData add(MenuDto menuDto) {
        this.menuService.addMenu(menuDto);
        return SUCCESS_TIP;
    }

    /**
     * 删除菜单
     */
    @Permission(Const.ADMIN_NAME)
    @BussinessLog(value = "删除菜单", key = "menuId", dict = DeleteDict.class)
    @RequestMapping("/remove")
    @ResponseBody
    public ResponseData remove(@RequestParam("menuId") Long menuId) {
        if (CoreUtil.isEmpty(menuId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        LogObjectHolder.me().set(ConstantFactory.me().getMenuName(menuId));
        this.menuService.delMenuContainSubMenus(menuId);
        return SUCCESS_TIP;
    }

    /**
     * 查看菜单
     */
    @RequestMapping("/view/{menuId}")
    @ResponseBody
    public ResponseData view(@PathVariable("menuId") Long menuId) {
        if (CoreUtil.isEmpty(menuId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Menu menu = this.menuService.getById(menuId);
        return ResponseData.success(menu);
    }

    /**
     * 修改菜单
     */
    @Permission(Const.ADMIN_NAME)
    @BussinessLog(value = "修改菜单", key = "name", dict = MenuDict.class)
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseData edit(MenuDto menuDto) {
        this.menuService.updateMenu(menuDto);

        return SUCCESS_TIP;
    }

    /**
     * 获取菜单列表
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(value = "menuName",required = false) String menuName,
                       @RequestParam(value = "level",required = false) String level,
                       @RequestParam(value = "menuId",required = false) Long menuId) {
        Page<Map<String, Object>> menus = this.menuService.selectMenus(menuName, level, menuId);
        Page<Map<String, Object>> wrap = new MenuWrapper(menus).wrap();
        return LayuiPageFactory.createPageInfo(wrap);
    }

    /**
     * 获取菜单树形
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping("/listTree")
    @ResponseBody
    public Object listTree(@RequestParam(value = "menuName",required = false) String menuName,
                           @RequestParam(value = "level",required = false) String level) {
        List<Map<String, Object>> menus = this.menuService.selectMenuTree(menuName, level);
        List<Map<String, Object>> wrap = new MenuWrapper(menus).wrap();
        LayuiPageInfo result = new LayuiPageInfo();
        result.setData(wrap);
        return result;
    }

    /**
     * 查询菜单数据
     */
    @RequestMapping("/getMenuInfo")
    @ResponseBody
    public ResponseData getMenuInfo(@RequestParam("menuId") Long menuId) {
        if (CoreUtil.isEmpty(menuId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Menu menu = this.menuService.getById(menuId);
        MenuDto menuDto = new MenuDto();
        CoreUtil.copyProperties(menu, menuDto);
        menuDto.setPid(ConstantFactory.me().getMenuIdByCode(menuDto.getPcode()));
        menuDto.setPcodeName(ConstantFactory.me().getMenuNameByCode(menuDto.getPcode()));
        return ResponseData.success(menuDto);
    }

    /**
     * 获取菜单树形
     */
    @RequestMapping("/menuTreeList")
    @ResponseBody
    public List<ZTreeNode> menuTreeList() {
        return this.menuService.menuTreeList();
    }

    /**
     * 获取菜单树形
     */
    @RequestMapping("/selectMenuTreeList")
    @ResponseBody
    public List<ZTreeNode> selectMenuTreeList() {
        List<ZTreeNode> roleTreeList = this.menuService.menuTreeList();
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }

    /**
     * 获取菜单树形
     */
    @RequestMapping("/menuTreeListByRoleId/{roleId}")
    @ResponseBody
    public List<ZTreeNode> menuTreeListByRoleId(@PathVariable("roleId") Long roleId) {
        List<Long> menuIds = this.menuService.getMenuIdsByRoleId(roleId);
        if (CoreUtil.isEmpty(menuIds)) {
            return this.menuService.menuTreeList();
        } else {
            return this.menuService.menuTreeListByMenuIds(menuIds);
        }
    }
}

