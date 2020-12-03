package com.common.platform.sys.modular.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.common.platform.auth.annotation.Permission;
import com.common.platform.base.config.sys.Const;
import com.common.platform.base.log.BussinessLog;
import com.common.platform.base.page.factory.LayuiPageFactory;
import com.common.platform.sys.modular.system.service.LoginLogService;
import com.common.platform.sys.modular.system.wrapper.LogWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.common.platform.sys.base.controller.BaseController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录记录 前端控制器
 * </p>
 */
@Controller
@RequestMapping("/loginLog")
public class LoginLogController extends BaseController {

    private static String PREFIX = "/modular/system/log/";

    @Autowired
    private LoginLogService loginLogService;

    /**
     * 跳转到登录日志首页
     */
    @RequestMapping("")
    public String index(){
        return PREFIX + "login_log.html";
    }

    /**
     * 查询登录日志的列表
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(value = "beginTime",required = false) String beginTime,
                       @RequestParam(value = "endTime",required = false) String endTime,
                       @RequestParam(value = "logName",required = false) String logName){
        Page page = LayuiPageFactory.defaultPage();
        List<Map<String,Object>> result = this.loginLogService.getLoginLogs(page,beginTime,endTime,logName);
        page.setRecords(new LogWrapper(result).wrap());

        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 清空日志
     */
    @Permission(Const.ADMIN_NAME)
    @BussinessLog("清空登录日志")
    @RequestMapping("/delLoginLog")
    @ResponseBody
    public Object delLog(){
        SqlRunner.db().delete("DELETE FROM sys_login_log");
        return SUCCESS_TIP;
    }

}

