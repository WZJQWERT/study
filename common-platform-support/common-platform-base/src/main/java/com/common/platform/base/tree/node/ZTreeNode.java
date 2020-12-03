package com.common.platform.base.tree.node;

import lombok.Data;

@Data
public class ZTreeNode {

    /**
     * 节点id
     */
    private Long id;

    /**
     * 父节点id
     */
    private Long pId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 是否打开节点
     */
    private Boolean open;

    /**
     * 是否被选中
     */
    private Boolean checked;

    /**
     * 节点图标
     */
    private String iconSkin;

    /**
     * 创建ZTree的父级节点
     */
    public static  ZTreeNode createParent(){
        ZTreeNode zTreeNode = new ZTreeNode();
        zTreeNode.setPId(0L);
        zTreeNode.setOpen(true);
        zTreeNode.setName("顶级");
        zTreeNode.setId(0L);
        zTreeNode.setChecked(true);
        return zTreeNode;
    }
}
