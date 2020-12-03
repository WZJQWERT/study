package com.common.platform.sys.modular.system.wrapper;

import com.common.platform.base.tree.node.TreeviewNode;

import java.util.List;

public class DeptTreeWrapper {

    public static void clearNull(List<TreeviewNode> list){
        if(list == null){

        }else{
            if(list.size()<=0){

            }else{
                for (TreeviewNode node : list){
                    if(node.getNodes()!=null){
                        if(node.getNodes().size()<=0){
                            node.setNodes(null);
                        }else{
                            clearNull(node.getNodes());
                        }
                    }
                }
            }
        }
    }
}
