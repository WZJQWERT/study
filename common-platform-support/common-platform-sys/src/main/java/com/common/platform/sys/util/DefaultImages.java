package com.common.platform.sys.util;

import com.common.platform.sys.listener.ConfigListener;

public class DefaultImages {

    /**
     * 默认的登录背景图片
     */
    public static String loginBg(){
        return ConfigListener.getConf().get("contextPath")
                +"/assets/common/images/login-register.jpg";
    }

    /**
     * 默认的用户图片Base64编码
     */
    public static String defaultAvatarUrl(){
        return ConfigListener.getConf().get("contextPath")
                + "/system/previewAvatar";
    }

    /**
     * 默认的404错误业务背景
     */
    public static String error404(){
        return ConfigListener.getConf().get("contextPath")
                +"/assets/common/images/error-bg.jpg";
    }
}
