package com.common.platform.sys.modular.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.platform.auth.annotation.Permission;
import com.common.platform.base.log.BussinessLog;
import com.common.platform.base.page.factory.LayuiPageFactory;
import com.common.platform.base.tree.factory.DefaultTreeBuildFactory;
import com.common.platform.base.tree.node.LayuiTreeNode;
import com.common.platform.base.tree.node.TreeviewNode;
import com.common.platform.base.tree.node.ZTreeNode;
import com.common.platform.base.utils.CoreUtil;
import com.common.platform.sys.base.controller.response.ResponseData;
import com.common.platform.sys.dictmap.DeptDict;
import com.common.platform.sys.exception.RequestEmptyException;
import com.common.platform.sys.factory.ConstantFactory;
import com.common.platform.sys.log.LogObjectHolder;
import com.common.platform.sys.modular.system.entity.Dept;
import com.common.platform.sys.modular.system.model.DeptDto;
import com.common.platform.sys.modular.system.service.DeptService;
import com.common.platform.sys.modular.system.wrapper.DeptTreeWrapper;
import com.common.platform.sys.modular.system.wrapper.DeptWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.common.platform.sys.base.controller.BaseController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {

    private String PREFIX = "/modular/system/dept/";

    @Autowired
    private DeptService deptService;

    /**
     * 跳转到部门首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dept.html";
    }

    /**
     * 跳转到添加部门
     */
    @RequestMapping("/dept_add")
    public String deptAdd() {
        return PREFIX + "dept_add.html";
    }

    /**
     * 跳转到修改部门
     */
    @Permission
    @RequestMapping("/dept_update")
    public String deptUpdate(@RequestParam("deptId") Long deptId) {

        if (CoreUtil.isEmpty(deptId)) {
            throw new RequestEmptyException();
        }
        Dept dept = this.deptService.getById(deptId);
        LogObjectHolder.me().set(dept);

        return PREFIX + "dept_edit.html";
    }

    /**
     * 获取部门的tree列表，layuiTree格式
     */
    @RequestMapping("/layuiTree")
    @ResponseBody
    public List<LayuiTreeNode> layuiTree() {
        List<LayuiTreeNode> list = this.deptService.layuiTree();
        list.add(LayuiTreeNode.createRoot());

        DefaultTreeBuildFactory<LayuiTreeNode> treeBuildFactory = new DefaultTreeBuildFactory<>();
        treeBuildFactory.setRootParentId("-1");
        return treeBuildFactory.doTreeBuild(list);
    }

    /**
     * 获取部门的tree列表，zTree格式
     */
    @RequestMapping("/tree")
    @ResponseBody
    public List<ZTreeNode> tree() {
        List<ZTreeNode> tree = this.deptService.tree();
        tree.add(ZTreeNode.createParent());
        return tree;
    }

    /**
     * 获取部门的tree列表，JqueryTree格式
     */
    @RequestMapping("/treeview")
    @ResponseBody
    public List<TreeviewNode> treeview() {
        List<TreeviewNode> treeviewNodes = this.deptService.treeviewNodes();

        DefaultTreeBuildFactory<TreeviewNode> factory = new DefaultTreeBuildFactory<>();
        factory.setRootParentId("0");
        List<TreeviewNode> results = factory.doTreeBuild(treeviewNodes);

        DeptTreeWrapper.clearNull(results);

        return results;
    }

    /**
     * 新增部门
     */
    @Permission
    @BussinessLog(value = "添加部门", key = "simpleName", dict = DeptDict.class)
    @RequestMapping("/add")
    @ResponseBody
    public ResponseData add(Dept dept) {
        this.deptService.addDept(dept);
        return SUCCESS_TIP;
    }

    /**
     * 获取所有部门列表
     */
    @Permission
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(value = "condition",required = false) String condition,
                       @RequestParam(value = "deptId",required = false) Long deptId){
        Page<Map<String,Object>> list = this.deptService.list(condition,deptId);
        Page<Map<String,Object>> wrap = new DeptWrapper(list).wrap();
        return LayuiPageFactory.createPageInfo(wrap);
    }

    /**
     * 获取部门详情
     */
    @Permission
    @RequestMapping("/detail/{deptId}")
    @ResponseBody
    public Object detail(@PathVariable("deptId") Long deptId){
        Dept dept = this.deptService.getById(deptId);
        DeptDto deptDto = new DeptDto();
        CoreUtil.copyProperties(dept,deptDto);
        deptDto.setPName(ConstantFactory.me().getDeptName(deptDto.getPid()));
        return deptDto;
    }

    /**
     * 修改部门
     */
    @Permission
    @BussinessLog(value = "修改部门",key = "simpleName",dict = DeptDict.class)
    @RequestMapping("/update")
    @ResponseBody
    public ResponseData update(Dept dept){
        this.deptService.editDept(dept);
        return SUCCESS_TIP;
    }

    @Permission
    @BussinessLog(value = "删除部门",key = "simpleName",dict = DeptDict.class)
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(@RequestParam("deptId") Long deptId){
        LogObjectHolder.me().set(ConstantFactory.me().getDeptName(deptId));
        this.deptService.deleteDept(deptId);
        return SUCCESS_TIP;
    }
}

