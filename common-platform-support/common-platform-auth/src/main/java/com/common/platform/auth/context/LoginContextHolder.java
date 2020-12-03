package com.common.platform.auth.context;

import com.common.platform.base.config.context.SpringContextHolder;

/**
 * 获取当前登录上下文接口
 */
public class LoginContextHolder {

    public static LoginContext getContext(){
        return SpringContextHolder.getBean(LoginContext.class);
    }
}
