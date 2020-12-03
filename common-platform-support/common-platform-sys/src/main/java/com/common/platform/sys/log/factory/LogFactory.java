package com.common.platform.sys.log.factory;

import com.common.platform.sys.modular.system.entity.LoginLog;
import com.common.platform.sys.modular.system.entity.OperationLog;
import com.common.platform.sys.state.LogSucceed;
import com.common.platform.sys.state.LogType;

import java.util.Date;

public class LogFactory {

    /**
     * 创建操作日志
     */
    public static OperationLog createOperationLog(LogType logType, Long userId,
                                                  String bussinessName, String clazzName,
                                                  String methodName, String msg, LogSucceed succeed){
        OperationLog operationLog = new OperationLog();
        operationLog.setClassName(clazzName);
        operationLog.setLogName(bussinessName);
        operationLog.setLogType(logType.getMessage());
        operationLog.setMessage(msg);
        operationLog.setMethod(methodName);
        operationLog.setSucceed(succeed.getMessage());
        operationLog.setUserId(userId);
        operationLog.setCreateTime(new Date());
        return operationLog;
    }

    /**
     * 创建登录日志
     */
    public static LoginLog createLoginLog(LogType logType,Long userId,String msg,String ip){
        LoginLog loginLog = new LoginLog();
        loginLog.setIpAddress(ip);
        loginLog.setLogName(logType.getMessage());
        loginLog.setMessage(msg);
        loginLog.setUserId(userId);
        loginLog.setCreateTime(new Date());
        loginLog.setSucceed(LogSucceed.SUCCESS.getMessage());
        return loginLog;
    }
}
