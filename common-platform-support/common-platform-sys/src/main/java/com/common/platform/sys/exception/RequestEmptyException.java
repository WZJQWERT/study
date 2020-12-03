package com.common.platform.sys.exception;

import com.common.platform.base.exception.ServiceException;

public class RequestEmptyException extends ServiceException {

    public RequestEmptyException(){
        super(400,"请求数据不完整或格式错误");
    }

    public RequestEmptyException(String errorMessage){
        super(400,errorMessage);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
