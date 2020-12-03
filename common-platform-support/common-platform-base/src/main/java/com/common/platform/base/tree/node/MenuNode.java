package com.common.platform.base.tree.node;

import com.common.platform.base.enums.YesOrNotEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public class MenuNode implements Comparable, Serializable {

    /**
     * 节点id
     */
    private Long id;

    /**
     * 菜单编码
     */
    private String code;

    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 菜单级别
     */
    private Integer levels;

    /**
     * 菜单排序
     */
    private Integer num;

    /**
     * 是否是菜单
     */
    private String ismenu;

    /**
     * 菜单的url
     */
    private String url;

    /**
     * 菜单的图标
     */
    private String icon;

    /**
     * 系统类型
     */
    private String systemType;

    /**
     * 子节点的集合
     */
    private List<MenuNode> children;

    /**
     * 查询子节点时的临时集合
     */
    private List<MenuNode> linkedList = new ArrayList<>();

    public MenuNode(){
        super();
    }

    public MenuNode(Long id,Long parentId){
        super();
        this.id = id ;
        this.parentId = parentId;
    }

    /**
     * 重写排序比较接口，首先根据等级排序，然后根据排序字段排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {
        MenuNode menuNode = (MenuNode) o;
        Integer levels = menuNode.getLevels();
        Integer num = menuNode.getNum();

        if(levels==null){
            levels = 0;
        }
        if(num==null){
            num = 0;
        }

        if(this.levels.compareTo(levels)==0){
            return this.num.compareTo(num);
        }else{
            return this.levels.compareTo(levels);
        }

    }

    private static List<MenuNode> mergeList(List<MenuNode> menuList, int rank, Map<Long,List<MenuNode>> listMap){
        //保存当次调用总共合并的元素
        int n;
        //保存当次调用总共合并的list
        Map<Long, List<MenuNode>> currentMap = new HashMap<>();
        //由于按等级从小到大排序，所以需要从后往前排序
        //判断该节点是否属于当前循环的等级，如果不是则跳出循环
        for(n=menuList.size()-1;n>=0 && menuList.get(n).levels==rank;n--){
            //判断之前的调用是否返回以该节点的id为key的map，有则设置为children列表
            if(listMap!=null && listMap.get(menuList.get(n).getId())!=null){
                menuList.get(n).setChildren(listMap.get(menuList.get(n).getId()));
            }
            if(menuList.get(n).getParentId()!=null && menuList.get(n).getParentId()!=0){
                //判断当前节点属于的pid是否已经创建了以该pid为key的键值对，没有则创建新的链表
                currentMap.computeIfAbsent(menuList.get(n).getParentId(),k-> new LinkedList<>());
                currentMap.get(menuList.get(n).getParentId()).add(0,menuList.get(n));
            }

        }
        if(n<0){
            return menuList;
        }else{
            return mergeList(new ArrayList<>(menuList.subList(0,n+1)),menuList.get(n).getLevels(),currentMap);
        }
    }

    public static List<MenuNode> buildTitle(List<MenuNode> nodes){
        if(nodes.size()<=0){
            return nodes;
        }

        //过滤非菜单项
        nodes.removeIf(node->!node.getIsmenu().equals(YesOrNotEnum.Y.name()));

        //对菜单排序
        Collections.sort(nodes);

        return mergeList(nodes,nodes.get(nodes.size()-1).getLevels(),null);
    }
}
