package com.common.platform.base.tree.factory;

import com.common.platform.base.tree.Tree;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class DefaultTreeBuildFactory<T extends Tree> extends AbstractTreeBuildFactory<T> {

    /**
     * 顶级节点的父节点id
     */
    private String rootParentId = "-1";

    private void buildChildNodes(List<T> totalNodes,T node,List<T> childNodeLists){
        if(totalNodes==null || node==null){
            return;
        }
        List<T> nodeSubLists = getSubChildsLevelOne(totalNodes,node);

        if(nodeSubLists.size()<=0){

        }else{
            for (T nodeSubList:nodeSubLists){
                buildChildNodes(totalNodes,nodeSubList,new ArrayList<>());
            }
        }
        childNodeLists.addAll(nodeSubLists);
        node.setChildrenNodes(childNodeLists);
    }

    private List<T> getSubChildsLevelOne(List<T> list,T node){
        List<T> nodeList = new ArrayList<>();
        for (T nodeItem : list){
            if(nodeItem.getNodeParentId().equals(node.getNodeId())){
                nodeList.add(nodeItem);
            }
        }
        return nodeList;
    }

    @Override
    protected List<T> beforeBuild(List<T> nodes) {
        //默认情况下，构建之前不做任何工作
        return nodes;
    }

    @Override
    protected List<T> executeBuilding(List<T> nodes) {
        for (T treeNode:nodes){
            this.buildChildNodes(nodes,treeNode,new ArrayList<>());
        }
        return nodes;
    }

    @Override
    protected List<T> afterBuild(List<T> nodes) {
        ArrayList<T> results = new ArrayList<>();
        for (T node:nodes){
            if(node.getNodeParentId().equals(rootParentId)){
                results.add(node);
            }
        }
        return results;
    }
}
