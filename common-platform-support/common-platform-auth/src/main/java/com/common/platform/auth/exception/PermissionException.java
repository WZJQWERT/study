package com.common.platform.auth.exception;

import com.common.platform.base.exception.AbstractBaseExceptionEnum;
import lombok.Data;

/**
 * 权限异常 （进行增删改查时没有权限）
 */
@Data
public class PermissionException extends RuntimeException {

    private Integer code;
    private String errorMessage;

    public PermissionException(){
        super("权限异常");
        this.code = 500;
        this.errorMessage = "权限异常";
    }

    public PermissionException(AbstractBaseExceptionEnum exception){
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception .getMessage();
    }
}
