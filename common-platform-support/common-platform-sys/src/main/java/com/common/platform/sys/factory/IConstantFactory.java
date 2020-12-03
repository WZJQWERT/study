package com.common.platform.sys.factory;

import com.common.platform.sys.modular.system.entity.Dict;
import com.common.platform.sys.modular.system.entity.Menu;

import java.util.List;

public interface IConstantFactory {

    /**
     * 获取字典名称
     */
    String getDictName(Long dictId);

    /**
     * 根据字典类型的名称和字典中的值获取对应的名称
     */
    String getDictsByName(String name,String code);

    /**
     * 获取字典名称根据字典的code
     */
    String getDictNameByCode(String dictCode);

    /**
     * 根据父级id获取字典数据
     */
    List<Dict> findInDict(Long id);

    /**
     * 获取部门名称
     */
    String getDeptName(Long deptId);

    /**
     * 获取子部门id
     */
    List<Long> getSubDeptId(Long deptId);

    /**
     * 获取父部门id
     */
    List<Long> getParentDeptIds(Long deptId);

    /**
     * 获取被缓存的对象
     */
    String getCacheObject(String para);

    /**
     * 获取用户的职位名称
     */
    String getPositionName(Long userId);

    /**
     * 获取用户的职位ids
     */
    String getPositionIds(Long userId);

    /**
     * 根据用户id获取用户名称
     */
    String getUserNameById(Long userId);

    /**
     * 根据用户id获取用户账号
     */
    String getUserAccountById(Long userId);

    /**
     * 根据角色id获取对应名称
     */
    String getSingleRoleName(Long roleId);

    /**
     * 根据角色id获取角色对应名称集合
     */
    String getRoleName(String roleIds);

    /**
     * 通过角色id获取对应的英文名称
     */
    String getSingleRoleTip(Long roleId);

    /**
     * 获取用户的性别
     */
    String getSexName(String sexCode);

    /**
     * 获取用户状态
     */
    String getStatusName(String status);

    /**
     * 获取菜单名称（多个）
     */
    String getMenuNames(String menuIds);

    /**
     * 获取菜单名称（单个）
     */
    String getMenuName(Long menuId);


    /**
     * 获取菜单数据通过code
     */
    Menu getMenuByCode(String code);

    /**
     * 获取菜单名称通过code
     */
    String getMenuNameByCode(String code);

    /**
     * 获取菜单的id通过code
     */
    Long getMenuIdByCode(String code);

    /**
     * 获取菜单状态
     */
    String getMenuStatusName(String status);

}
