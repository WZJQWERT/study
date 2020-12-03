package com.common.platform.sys.state;

import lombok.Getter;

@Getter
public enum ManagerStatus {

    OK("ENABLE","启用"),FREEZED("LOCKED","冻结"),DELETED("DELETED","被删除");

    String code;
    String message;

    ManagerStatus(String code,String message){
        this.code = code;
        this.message = message;
    }

    public static String getDescription(String status){
        if(status==null){
            return "";
        }else{
            for (ManagerStatus s:ManagerStatus.values()){
                if(s.getCode().equals(status)){
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
