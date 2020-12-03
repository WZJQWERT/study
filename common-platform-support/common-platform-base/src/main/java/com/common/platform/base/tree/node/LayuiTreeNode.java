package com.common.platform.base.tree.node;

import com.common.platform.base.tree.Tree;
import com.common.platform.base.utils.CoreUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LayuiTreeNode implements Tree {

    /**
     * 节点id
     */
    private Long id;

    /**
     * 父节点id
     */
    private Long pid;

    /**
     * 节点名称
     */
    private String title;

    /**
     * 节点是否初始展开
     */
    private Boolean spread;

    /**
     * 节点是否为选中状态
     */
    private Boolean checked;

    /**
     * 节点是否为禁用状态
     */
    private Boolean disabled;

    private List<LayuiTreeNode> children = new ArrayList<>();


    @Override
    public String getNodeId() {
        if(CoreUtil.isNotEmpty(id)){
            return String.valueOf(id);
        }else{
            return null;
        }
    }

    @Override
    public String getNodeParentId() {
        if(CoreUtil.isNotEmpty(pid)){
            return String.valueOf(pid);
        }else{
            return null;
        }
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
         this.children = childrenNodes;
    }

    /**
     * 生成Layui Tree 父节点
     */
    public static LayuiTreeNode createRoot(){
        LayuiTreeNode layuiTreeNode = new LayuiTreeNode();
        layuiTreeNode.setChecked(true);
        layuiTreeNode.setId(0L);
        layuiTreeNode.setTitle("顶级");
        layuiTreeNode.setSpread(true);
        layuiTreeNode.setPid(-1L);
        return layuiTreeNode;
    }
}
