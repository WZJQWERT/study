package com.common.platform.sys.modular.system.controller;


import com.common.platform.base.page.LayuiPageInfo;
import com.common.platform.base.tree.node.ZTreeNode;
import com.common.platform.sys.base.controller.response.ResponseData;
import com.common.platform.sys.base.controller.response.SuccessResponseData;
import com.common.platform.sys.exception.RequestEmptyException;
import com.common.platform.sys.modular.system.entity.Dict;
import com.common.platform.sys.modular.system.entity.DictType;
import com.common.platform.sys.modular.system.model.params.DictParam;
import com.common.platform.sys.modular.system.model.result.DictResult;
import com.common.platform.sys.modular.system.service.DictService;
import com.common.platform.sys.modular.system.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.common.platform.sys.base.controller.BaseController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 基础字典 前端控制器
 * </p>
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

    private String PREFIX = "/modular/system/dict";

    @Autowired
    private DictService dictService;
    @Autowired
    private DictTypeService dictTypeService;

    /**
     * 跳转到主页面
     */
    @RequestMapping("")
    public String index(@RequestParam("dictTypeId") Long dictTypeId, Model model){
        model.addAttribute("dictTypeId",dictTypeId);
        DictType dictType = dictTypeService.getById(dictTypeId);
        if(dictType==null){
            throw new RequestEmptyException();
        }
        model.addAttribute("dictTypeName",dictType.getName());
        return PREFIX + "/dict.html";
    }

    /**
     * 新增页面
     */
    @RequestMapping("/add")
    public String add(@RequestParam("dictTypeId") Long dictTypeId,Model model){
        model.addAttribute("dictTypeId",dictTypeId);
        DictType dictType = dictTypeService.getById(dictTypeId);
        if(dictType==null){
            throw new RequestEmptyException();
        }
        model.addAttribute("dictTypeName",dictType.getName());
        return PREFIX + "/dict_add.html";
    }

    /**
     * 编辑页面
     */
    @RequestMapping("/edit")
    public String edit(@RequestParam("dictId") Long dictId,Model model){
        Dict dict = dictService.getById(dictId);
        if(dict==null){
            throw new RequestEmptyException();
        }
        DictType dictType = dictTypeService.getById(dict.getDictTypeId());
        if(dictType==null){
            throw new RequestEmptyException();
        }
        model.addAttribute("dictTypeId",dict.getDictTypeId());
        model.addAttribute("dictTypeName",dictType.getName());

        return PREFIX + "/dict_edit.html";
    }

    /**
     * 新增接口
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(DictParam dictParam){
        this.dictService.add(dictParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(DictParam dictParam){
        this.dictService.update(dictParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(DictParam dictParam){
        this.dictService.delete(dictParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(DictParam dictParam){
        DictResult dictResult = this.dictService.dictDetail(dictParam.getDictId());
        return ResponseData.success(dictResult);
    }

    /**
     * 查询列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public LayuiPageInfo list(DictParam dictParam){
        return this.dictService.findPageBySpec(dictParam);
    }

    /**
     * 获取某个字典类型下的所有字典
     */
    @RequestMapping("/listDicts")
    @ResponseBody
    public ResponseData listDicts(@RequestParam("dictTypeId") Long dictTypeId){
        List<Dict> dicts = this.dictService.listDicts(dictTypeId);
        return new SuccessResponseData(dicts);
    }

    /**
     * 获取某个字典类型下的所有字典
     */
    @RequestMapping("/listDictsByCode")
    @ResponseBody
    public ResponseData listDictsByCode(@RequestParam("dictTypeCode") String dictTypeCode){
        List<Dict> dicts = this.dictService.listDictsByCode(dictTypeCode);
        return new SuccessResponseData(dicts);
    }

    /**
     * 获取某个字典类型下的树列表，ZTree类型
     */
    @RequestMapping("/ztree")
    @ResponseBody
    public List<ZTreeNode> ztree(@RequestParam("dictTypeId") Long dictTypeId,
                                 @RequestParam(value = "dictId",required = false) Long dictId){
        return this.dictService.dictTreeList(dictTypeId,dictId);
    }

}

