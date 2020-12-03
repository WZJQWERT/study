package com.common.platform.sys.base.controller.response;

public class ErrorResponseData extends ResponseData {

    public ErrorResponseData(String message){
        super(false,DEFAULT_ERROR_CODE,message,null);
    }

    public ErrorResponseData(Integer code,String message){
        super(false,code,message,null);
    }

    public ErrorResponseData(Integer code,String message,Object object){
        super(false,code,message,object);
    }
}
