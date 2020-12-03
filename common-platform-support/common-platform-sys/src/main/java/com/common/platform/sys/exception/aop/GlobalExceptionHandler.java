package com.common.platform.sys.exception.aop;

import com.common.platform.auth.context.LoginContextHolder;
import com.common.platform.auth.exception.AuthException;
import com.common.platform.auth.exception.PermissionException;
import com.common.platform.auth.exception.enums.AuthExceptionEnum;
import com.common.platform.base.config.mail.MailUtil;
import com.common.platform.base.exception.BizExceptionEnum;
import com.common.platform.base.exception.ServiceException;
import com.common.platform.sys.base.controller.response.ErrorResponseData;
import com.common.platform.sys.exception.InvalidKaptchaException;
import com.common.platform.sys.log.LogManager;
import com.common.platform.sys.log.factory.LogTaskFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.common.platform.base.config.context.HttpContext.getIp;
import static com.common.platform.base.config.context.HttpContext.getRequest;

@ControllerAdvice
@Order(-100)
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MailUtil mailUtil;

    /**
     * 认证异常--认证失败（账号密码错误，账号冻结，token过期）
     */
    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponseData unAuth(AuthException e){
        return new ErrorResponseData(e.getCode(),e.getErrorMessage());
    }

    /**
     * 认证异常--权限异常
     */
    @ExceptionHandler(PermissionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponseData permissionException(PermissionException e){
        return new ErrorResponseData(e.getCode(),e.getErrorMessage());
    }

    /**
     * 认证异常--验证码异常
     */
    @ExceptionHandler(InvalidKaptchaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseData credentials(InvalidKaptchaException e){
        //进行日志保存
        String username = getRequest().getParameter("username");
        LogManager.me().executeLog(LogTaskFactory.loginLog(username,"验证码错误",getIp()));
        mailUtil.send("验证码相关",
                "错误码："+AuthExceptionEnum.VALID_CODE_ERROR.getCode()
                        +",错误信息："+AuthExceptionEnum.VALID_CODE_ERROR.getMessage());
        return new ErrorResponseData(AuthExceptionEnum.VALID_CODE_ERROR.getCode(),
                AuthExceptionEnum.VALID_CODE_ERROR.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseData bussiness(ServiceException e){
        //进行日志保存
        if(LoginContextHolder.getContext().hasLogin()){
            LogManager.me().executeLog(LogTaskFactory.exceptionLog(LoginContextHolder.getContext().getUserId(),e));
        }
        logger.error("业务相关",e);
        mailUtil.send("业务相关","错误码："+e.getCode()+",错误信息："+e.getMessage());
        return new ErrorResponseData(e.getCode(),e.getMessage());
    }

    /**
     * 未知异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseData notFound(RuntimeException e){
        //进行日志保存
        if(LoginContextHolder.getContext().hasLogin()){
            LogManager.me().executeLog(LogTaskFactory.exceptionLog(LoginContextHolder.getContext().getUserId(),e));
        }
        logger.error("未知相关",e);
        mailUtil.send("未知相关",
                "错误码："+BizExceptionEnum.SERVER_ERROR.getCode()
                        +",错误信息："+BizExceptionEnum.SERVER_ERROR.getMessage());
        return new ErrorResponseData(BizExceptionEnum.SERVER_ERROR.getCode(),
                BizExceptionEnum.SERVER_ERROR.getMessage());
    }
}
