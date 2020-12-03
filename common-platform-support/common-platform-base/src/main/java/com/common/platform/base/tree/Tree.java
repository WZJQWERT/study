package com.common.platform.base.tree;

import java.util.List;

public interface Tree {

    /**
     * 获取节点id
     */
    String getNodeId();

    /**
     * 获取父节点id
     */
    String getNodeParentId();

    /**
     * 设置子节点children
     */
    void setChildrenNodes(List childrenNodes);
}
