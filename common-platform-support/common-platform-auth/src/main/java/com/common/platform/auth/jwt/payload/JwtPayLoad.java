package com.common.platform.auth.jwt.payload;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class JwtPayLoad {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户的键
     */
    private String userKey;

    public JwtPayLoad(){

    }

    public JwtPayLoad(Long userId,String account,String userKey){
        this.userId = userId;
        this.account = account;
        this.userKey = userKey;
    }

    /**
     * 转化为map
     */
    public Map<String,Object> toMap(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("userId",this.userId);
        map.put("account",this.account);
        map.put("userKey",this.userKey);
        return map;
    }

    /**
     * 转化为bean
     */
    public static JwtPayLoad toBean(Map<String,Object> map){
        if(map==null || map.size()<=0){
            return new JwtPayLoad();
        }else{
            JwtPayLoad jwtPayLoad = new JwtPayLoad();
            Object userId = map.get("userId");
            if(userId instanceof Integer){
                jwtPayLoad.setUserId(Long.valueOf(map.get("userId").toString()));
            }
            jwtPayLoad.setAccount((String) map.get("account"));
            jwtPayLoad.setUserKey((String)map.get("userKey"));
            return jwtPayLoad;
        }
    }
}
