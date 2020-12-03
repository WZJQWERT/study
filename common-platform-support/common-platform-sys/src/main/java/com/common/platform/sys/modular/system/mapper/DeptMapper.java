package com.common.platform.sys.modular.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.platform.base.tree.node.LayuiTreeNode;
import com.common.platform.base.tree.node.TreeviewNode;
import com.common.platform.base.tree.node.ZTreeNode;
import com.common.platform.sys.modular.system.entity.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 */
public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * 获取ZTree的节点列表
     */
    List<ZTreeNode> tree();

    /**
     * 获取LayuiTree的节点列表
     */
    List<LayuiTreeNode> layuiTree();

    /**
     * 获取所有部门的树列表
     */
    List<TreeviewNode> treeviewNodes();

    /**
     * 获取所有部门的分页
     */
    Page<Map<String,Object>> list(@Param("page") Page page,@Param("condition") String condition,@Param("deptId") Long deptId);

    /**
     * 获取相关部门
     */
    List<Dept> likePids(@Param("deptId") Long deptId);
}
