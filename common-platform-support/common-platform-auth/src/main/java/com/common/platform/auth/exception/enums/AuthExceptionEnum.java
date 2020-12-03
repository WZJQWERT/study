package com.common.platform.auth.exception.enums;

import com.common.platform.base.exception.AbstractBaseExceptionEnum;
import lombok.Getter;

@Getter
public enum AuthExceptionEnum implements AbstractBaseExceptionEnum {
    NOT_LOGIN_ERROR(1401,"用户未登录"),
    USERNAME_PWD_ERROR(1402,"账号密码错误"),
    LONGIN_EXPIRED(1403, "登录已过期，请重新登录"),
    ACCOUNT_FREEZE_ERROR(1404,"账号被冻结"),
    NOT_ROLE_ERROR(1405,"用户没有分配角色，获取数据失败"),
    VALID_CODE_ERROR(1406,"验证码错误"),
    NO_PERMISSION(1407,"没有访问权限"),
    SESSION_TIMEOUT(1408,"登录会话超时");

    private Integer code;
    private String message;

    AuthExceptionEnum(int code,String message){
        this.code = code;
        this.message = message;
    }

}
