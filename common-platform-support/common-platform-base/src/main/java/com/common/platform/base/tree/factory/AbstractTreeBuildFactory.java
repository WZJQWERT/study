package com.common.platform.base.tree.factory;

import java.util.List;

public abstract class AbstractTreeBuildFactory<T> {

    /**
     * 树节点构建的整个过程
     */
    public List<T> doTreeBuild(List<T> nodes){

        //构建前节点的处理工作
        List<T> readyToBuild = beforeBuild(nodes);
        //具体的构建过程
        List<T> builded = executeBuilding(readyToBuild);
        //构建之后节点的处理工作
        return afterBuild(builded);
    }

    /**
     * 构建前节点的处理工作
     */
    protected abstract List<T> beforeBuild(List<T> nodes);

    /**
     * 具体的构建过程
     */
    protected abstract List<T> executeBuilding(List<T> nodes);

    /**
     * 构建之后节点的处理工作
     */
    protected abstract List<T> afterBuild(List<T> nodes);
}
