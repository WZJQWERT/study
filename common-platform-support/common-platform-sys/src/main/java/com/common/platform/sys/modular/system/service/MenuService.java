package com.common.platform.sys.modular.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.platform.base.enums.CommonStatus;
import com.common.platform.base.exception.BizExceptionEnum;
import com.common.platform.base.exception.ServiceException;
import com.common.platform.base.page.factory.LayuiPageFactory;
import com.common.platform.base.tree.node.MenuNode;
import com.common.platform.base.tree.node.ZTreeNode;
import com.common.platform.base.utils.CoreUtil;
import com.common.platform.sys.exception.RequestEmptyException;
import com.common.platform.sys.factory.ConstantFactory;
import com.common.platform.sys.listener.ConfigListener;
import com.common.platform.sys.modular.system.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.common.platform.sys.modular.system.mapper.MenuMapper;
import com.common.platform.sys.modular.system.model.MenuDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper,Menu> {

    /**
     * 添加菜单
     */
    @Transactional
    public void addMenu(MenuDto menuDto){

        if(CoreUtil.isOneEmpty(menuDto,menuDto.getCode(),menuDto.getName(),
                menuDto.getPid(),menuDto.getMenuFlag(),menuDto.getUrl(),menuDto.getSystemType())){
            throw new RequestEmptyException();
        }
        String existedMenuName = ConstantFactory.me().getMenuNameByCode(menuDto.getCode());
        if(CoreUtil.isNotEmpty(existedMenuName)){
            throw new ServiceException(BizExceptionEnum.EXISTED_THE_MENU);
        }
        Menu resultMenu = this.menuSetPcode(menuDto);
        resultMenu.setStatus(CommonStatus.ENABLE.getCode());
        this.save(resultMenu);
    }

    /**
     * 更新菜单
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(MenuDto menuDto){
        if(CoreUtil.isOneEmpty(menuDto,menuDto.getMenuId(),menuDto.getCode())){
            throw new RequestEmptyException();
        }
        Long id = menuDto.getMenuId();
        Menu menu = this.getById(id);

        if(menu==null){
            throw new RequestEmptyException();
        }

        Menu resultMenu = this.menuSetPcode(menuDto);
        updateSubMenuLevels(menu,resultMenu);

        this.updateById(resultMenu);

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateSubMenuLevels(Menu oldMenu,Menu newMenu){
        List<Menu> menus = this.baseMapper.getMenusLikePcodes(oldMenu.getCode());

        for(Menu menu : menus){
            if(oldMenu.getCode().equals(menu.getPcode())){
                menu.setPcode(newMenu.getCode());
            }
            String oldPcodesPrefix = oldMenu.getPcodes() + "[" + oldMenu.getCode() + "],";
            String oldPcodesSuffix = menu.getPcodes().substring(oldPcodesPrefix.length());
            String menuPcodes = newMenu.getPcodes() + "[" + newMenu.getCode()+"]," + oldPcodesSuffix;
            menu.setPcodes(menuPcodes);

            int level = StrUtil.count(menuPcodes,"[");
            menu.setLevels(level);

            menu.setSystemType(newMenu.getSystemType());

            this.updateById(menu);
        }
    }

    /**
     * 删除菜单
     */
    @Transactional
    public void delMenu(Long menuId){
        this.baseMapper.deleteById(menuId);
        this.baseMapper.deleteRelationByMenu(menuId);
    }

    /**
     * 删除菜单包含的所有子菜单
     */
    @Transactional
    public void delMenuContainSubMenus(Long menuId){
        Menu menu = this.baseMapper.selectById(menuId);
        delMenu(menuId);

        List<Menu> menus = this.baseMapper.getMenusLikePcodes(menu.getCode());
        for (Menu temp: menus){
            delMenu(temp.getMenuId());
        }
    }

    /**
     * 根据条件查询菜单
     */
    public Page<Map<String,Object>> selectMenus(String condition ,String level, Long menuId){
        String code="";
        if(menuId != null && menuId!=0L){
            Menu menu = this.getById(menuId);
            code = menu.getCode();
        }
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.selectMenus(page,condition,level,menuId,code);
    }

    /**
     * 根据条件查询菜单
     */
    public List<Long> getMenuIdsByRoleId(Long roleId){
        return this.baseMapper.getMenuIdsByRoleId(roleId);
    }

    /**
     * 获取菜单树
     */
    public List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds){
        return this.baseMapper.menuTreeListByMenuIds(menuIds);
    }

    /**
     * 获取菜单树
     */
    public List<ZTreeNode> menuTreeList(){
        return this.baseMapper.menuTreeList();
    }

    /**
     * 删除menu关联的relation
     */
    public int deleteRelationByMenu(Long menuId){
        return this.baseMapper.deleteRelationByMenu(menuId);
    }

    /**
     * 获取资源url通过角色id
     */
    public List<String> getResUrlsByRoleId(Long roleId){
        return this.baseMapper.getResUrlsByRoleId(roleId);
    }

    /**
     * 根据角色获取菜单
     */
    public List<MenuNode> getMenusByRoleIds(List<Long> roleIds){
        List<MenuNode> menus = this.baseMapper.getMenusByRoleIds(roleIds);
        for (MenuNode node: menus){
            node.setUrl(ConfigListener.getConf().get("contextPath") + node.getUrl());
        }
        return menus;
    }

    /**
     * 通过code查找菜单
     */
    public Menu selectByCode(String code){
        Menu menu = new Menu();
        menu.setCode(code);
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>(menu);
        return this.baseMapper.selectOne(queryWrapper);
    }

    /**
     * 获取菜单树形
     */
    public List<Map<String,Object>> selectMenuTree(String condition,String level){
        List<Map<String,Object>> maps = this.baseMapper.selectMenuTree(condition,level);
        if(maps == null){
            maps = new ArrayList<>();
        }
        if(CoreUtil.isNotEmpty(condition) || CoreUtil.isNotEmpty(level)){
            if(maps.size()>0){
                for(Map<String,Object> menu:maps){
                    menu.put("pcode","0");
                }
            }
        }

        Menu menu = new Menu();
        menu.setMenuId(-1L);
        menu.setName("根节点");
        menu.setCode("0");
        menu.setPcode("-2");
        maps.add(BeanUtil.beanToMap(menu));

        return maps;
    }

    public Menu menuSetPcode(MenuDto menuDto){
        Menu resultMenu = new Menu();
        CoreUtil.copyProperties(menuDto,resultMenu);
        if(CoreUtil.isEmpty(menuDto.getPid()) || menuDto.getPid().equals(0L)){
            resultMenu.setPcode("0");
            resultMenu.setPcodes("[0],");
            resultMenu.setLevels(1);
        }else{
            Long pid = menuDto.getPid();
            Menu pMenu = this.getById(pid);
            Integer pLevels = pMenu.getLevels();
            resultMenu.setPcode(pMenu.getCode());

            if(menuDto.getCode().equals(menuDto.getPcode())){
                throw new ServiceException(BizExceptionEnum.MENU_PCODE_COINCIDENCE);
            }

            resultMenu.setLevels(pLevels+1);
            resultMenu.setPcodes(pMenu.getPcodes() + "["+pMenu.getCode()+"],");
        }
        return resultMenu;
    }
}
