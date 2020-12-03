package com.common.platform.sys.modular.config.controller;


import com.common.platform.base.page.LayuiPageInfo;
import com.common.platform.base.utils.CoreUtil;
import com.common.platform.sys.base.controller.response.ResponseData;
import com.common.platform.sys.modular.config.entity.Config;
import com.common.platform.sys.modular.config.model.params.ConfigParam;
import com.common.platform.sys.modular.config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.common.platform.sys.base.controller.BaseController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 参数配置 前端控制器
 * </p>
 */
@Controller
@RequestMapping("/sysConfig")
public class ConfigController extends BaseController {

    private String PREFIX = "/modular/sysConfig";

    @Autowired
    private ConfigService configService;

    /**
     * 跳转到配置首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/sysConfig.html";
    }

    /**
     * 跳转到新增页面
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/sysConfig_add.html";
    }

    /**
     * 跳转到编辑页面
     */
    @RequestMapping("/edit")
    public String edit(){
        return PREFIX + "/sysConfig_edit.html";
    }

    /**
     * 新增接口
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ConfigParam configParam){
        this.configService.add(configParam);
        return SUCCESS_TIP;
    }

    /**
     * 编辑接口
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(ConfigParam configParam){
        this.configService.update(configParam);
        return SUCCESS_TIP;
    }

    /**
     * 删除接口
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(ConfigParam configParam){
        this.configService.delete(configParam);
        return SUCCESS_TIP;
    }

    /**
     * 查看详情接口
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(ConfigParam configParam){
        Config config = this.configService.getById(configParam.getId());
        return ResponseData.success(config);
    }

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public LayuiPageInfo list(@RequestParam(value = "condition",required = false) String condition){
        ConfigParam configParam = new ConfigParam();
        if(CoreUtil.isNotEmpty(condition)){
            configParam.setCode(condition);
            configParam.setName(condition);
            configParam.setValue(condition);
            configParam.setRemark(condition);
        }
        return this.configService.findPageBySpec(configParam);
    }
}

