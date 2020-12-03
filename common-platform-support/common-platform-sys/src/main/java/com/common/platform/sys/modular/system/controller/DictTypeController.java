package com.common.platform.sys.modular.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.platform.base.page.LayuiPageInfo;
import com.common.platform.sys.base.controller.response.ResponseData;
import com.common.platform.sys.base.controller.response.SuccessResponseData;
import com.common.platform.sys.modular.system.entity.DictType;
import com.common.platform.sys.modular.system.model.params.DictTypeParam;
import com.common.platform.sys.modular.system.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.common.platform.sys.base.controller.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 字典类型表 前端控制器
 * </p>
 */
@Controller
@RequestMapping("/dictType")
public class DictTypeController extends BaseController {

    private String PREFIX = "modular/system/dictType";

    @Autowired
    private DictTypeService dictTypeService;

    /**
     * 跳转到主页面
     */
    @RequestMapping("")
    public String index(){
        return PREFIX + "/dictType.html";
    }

    /**
     * 新增页面
     */
    @RequestMapping("/add")
    public String add(){
        return PREFIX + "/dictType_add.html";
    }

    /**
     * 编辑页面
     */
    @RequestMapping("/edit")
    public String edit(){
        return PREFIX + "/dictType_edit.html";
    }

    /**
     * 新增接口
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(DictTypeParam dictTypeParam){
        this.dictTypeService.add(dictTypeParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(DictTypeParam dictTypeParam){
        this.dictTypeService.update(dictTypeParam);
        return SUCCESS_TIP;
    }

    /**
     * 删除接口
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(DictTypeParam dictTypeParam){
        this.dictTypeService.delete(dictTypeParam);
        return SUCCESS_TIP;
    }

    /**
     * 查看详情接口
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(DictTypeParam dictTypeParam){
        DictType dictType = this.dictTypeService.getById(dictTypeParam.getDictTypeId());
        return ResponseData.success(dictType);
    }

    /**
     * 查询列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public LayuiPageInfo list(DictTypeParam dictTypeParam){
        return this.dictTypeService.findPageBySpec(dictTypeParam);
    }

    /**
     * 查询所有字典
     */
    @RequestMapping("/listTypes")
    @ResponseBody
    public ResponseData listTypes(){
        QueryWrapper<DictType> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.select("dict_type_id","code","name");
        List<DictType> list = this.dictTypeService.list(objectQueryWrapper);
        return new SuccessResponseData(list);
    }

}

