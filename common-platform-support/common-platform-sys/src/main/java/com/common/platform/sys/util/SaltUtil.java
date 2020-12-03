package com.common.platform.sys.util;

import com.common.platform.base.utils.CoreUtil;
import com.common.platform.base.utils.MD5Util;

import java.security.NoSuchAlgorithmException;

public class SaltUtil {

    /**
     * 获取密码盐
     */
    public static String getRandomSalt(){
        return CoreUtil.getRandomString(5);
    }

    /**
     * MD5加密，带加密盐
     */
    public static String md5Encrypt(String password,String salt){
        if(CoreUtil.isOneEmpty(password,salt)){
            throw new IllegalStateException("密码或加密盐为空");
        }else{
            try {
                return MD5Util.encrypt(password + salt);
            }catch (NoSuchAlgorithmException e){
                throw new IllegalStateException("密码加密失败");
            }
        }
    }
}
