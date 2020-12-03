package com.common.platform.sys.modular.system.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.common.platform.auth.annotation.Permission;
import com.common.platform.base.config.sys.Const;
import com.common.platform.base.log.BizLogType;
import com.common.platform.base.log.BussinessLog;
import com.common.platform.base.page.factory.LayuiPageFactory;
import com.common.platform.sys.modular.system.entity.OperationLog;
import com.common.platform.sys.modular.system.service.OperationLogService;
import com.common.platform.sys.modular.system.wrapper.LogWrapper;
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
 * 操作日志 前端控制器
 * </p>
 */
@Controller
@RequestMapping("/log")
public class OperationLogController extends BaseController {

    private static String PREFIX = "/modular/system/log/";

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 跳转到操作日志的首页
     */
    @RequestMapping("")
    public String index(){
        return PREFIX + "log.html";
    }

    /**
     * 查询操作日志列表
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(value = "beginTime",required = false) String beginTime,
                       @RequestParam(value = "endTime",required = false) String endTime,
                       @RequestParam(value = "logName",required = false) String logName,
                       @RequestParam(value = "logType",required = false) Integer logType){
        Page page = LayuiPageFactory.defaultPage();
        List<Map<String,Object>> result = this.operationLogService.getOperationLogs(page,beginTime,
                endTime,logName, BizLogType.valueOf(logType));
        page.setRecords(new LogWrapper(result).wrap());

        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 清空日志
     */
    @Permission(Const.ADMIN_NAME)
    @BussinessLog("清空操作日志")
    @RequestMapping("/delLog")
    @ResponseBody
    public Object delLog(){
        SqlRunner.db().delete("DELETE FROM sys_operation_log");
        return SUCCESS_TIP;
    }

    /**
     * 查询操作日志详情
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping("/detail/{id}")
    @ResponseBody
    public Object detail(@PathVariable("id") Long id){
        OperationLog operationLog = this.operationLogService.getById(id);
        Map<String,Object> stringObjectMap = BeanUtil.beanToMap(operationLog);
        return super.warpObject(new LogWrapper(stringObjectMap));
    }

}

