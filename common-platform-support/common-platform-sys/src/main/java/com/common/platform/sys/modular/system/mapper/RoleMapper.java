package com.common.platform.sys.modular.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.platform.base.tree.node.ZTreeNode;
import com.common.platform.sys.modular.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据条件查询角色列表
     */
    Page<Map<String,Object>> selectRoles(@Param("page") Page page,
                                         @Param("condition") String condition);

    /**
     * 删除某个角色的所有权限
     */
    int deleteRolesById(@Param("roleId") Long roleId);

    /**
     * 获取角色的树形
     */
    List<ZTreeNode> roleTreeList();

    /**
     * 获取角色的树形
     */
    List<ZTreeNode> roleTreeListByRoleId(Long[] roleId);

    /**
     * 角色列表
     */
    IPage<Map<String,Object>> listRole(Page pageContext,@Param("name") String name);
}
