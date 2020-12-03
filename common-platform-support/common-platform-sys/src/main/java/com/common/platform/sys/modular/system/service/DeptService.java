package com.common.platform.sys.modular.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.platform.base.exception.BizExceptionEnum;
import com.common.platform.base.exception.ServiceException;
import com.common.platform.base.page.factory.LayuiPageFactory;
import com.common.platform.base.tree.node.LayuiTreeNode;
import com.common.platform.base.tree.node.TreeviewNode;
import com.common.platform.base.tree.node.ZTreeNode;
import com.common.platform.base.utils.CoreUtil;
import com.common.platform.sys.modular.system.entity.Dept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.common.platform.sys.modular.system.mapper.DeptMapper;
import org.springframework.stereotype.Service;

import javax.xml.soap.Detail;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 服务类
 * </p>
 */
@Service
public class DeptService extends ServiceImpl<DeptMapper, Dept> {

    /**
     * 新增部门
     */
    public void addDept(Dept dept){
        if(CoreUtil.isOneEmpty(dept,dept.getSimpleName(),dept.getFullName(),dept.getPid(),dept.getDescription())){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.deptSetPids(dept);
        this.save(dept);
    }

    /**
     *修改部门
     */
    public void editDept(Dept dept){
        if(CoreUtil.isOneEmpty(dept,dept.getDeptId(), dept.getSimpleName(),dept.getFullName(),dept.getPid())){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.deptSetPids(dept);
        this.updateById(dept);
    }

    /**
     * 删除部门
     */
    public void deleteDept(Long deptId){
        Dept dept = this.getById(deptId);

        List<Dept> subDepts = this.baseMapper.likePids(deptId);
        for(Dept temp: subDepts){
            this.removeById(temp.getDeptId());
        }

        this.removeById(deptId);
    }

    /**
     * 获取ZTree的节点列表
     */
    public List<ZTreeNode> tree(){
        return this.baseMapper.tree();
    }

    /**
     * 获取Layui Tree的节点列表
     */
    public List<LayuiTreeNode> layuiTree(){
        return this.baseMapper.layuiTree();
    }

    /**
     * 获取Jquery Tree的节点列表
     */
    public List<TreeviewNode> treeviewNodes(){
        return this.baseMapper.treeviewNodes();
    }

    /**
     * 获取部门所有列表
     */
    public Page<Map<String,Object>> list(String condition,Long deptId){
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.list(page,condition,deptId);
    }

    private void deptSetPids(Dept dept){
        if(CoreUtil.isEmpty(dept.getPid()) || dept.getPid().equals(0L)){
            dept.setPid(0L);
            dept.setPids("[0],");
        }else{
            Long pid = dept.getPid();
            Dept temp = this.getById(pid);
            String pids = temp.getPids();
            dept.setPid(pid);
            dept.setPids(pids + "[" + pid + "],");
        }
    }

}
