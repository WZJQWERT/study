package com.common.platform.sys.auth.cache;

import com.alibaba.fastjson.JSON;
import com.common.platform.auth.pojo.LoginUser;
import com.common.platform.base.config.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DefaultSessionManager implements SessionManager {

    private Map<String,LoginUser> caches = new ConcurrentHashMap<>();

    @Autowired
    private RedisUtil redisUtil;

    @Value("${login.session.cache.type}")
    private String cacheType;

    private static String CACHE_REDIS = "redis";

    @Override
    public void createSession(String token, LoginUser loginUser) {
        if(CACHE_REDIS.equals(cacheType)){
            createRedisSession(getKey(token),loginUser);
        }else{
            createLocalSession(getKey(token),loginUser);
        }
    }

    public void createLocalSession(String key, LoginUser loginUser){
        caches.put(key,loginUser);
    }

    public void createRedisSession(String key, LoginUser loginUser){
        redisUtil.set(key, JSON.toJSONString(loginUser));
    }

    @Override
    public LoginUser getSession(String token) {
        if(CACHE_REDIS.equals(cacheType)){
            return getRedisSession(getKey(token));
        }else{
            return getLocalSession(getKey(token));
        }
    }

    public LoginUser getLocalSession(String key){
        return caches.get(key);
    }

    public LoginUser getRedisSession(String key){
        return JSON.parseObject((String)redisUtil.get(key),LoginUser.class);
    }

    @Override
    public void removeSession(String token) {
        if(CACHE_REDIS.equals(cacheType)){
            removeRedisSession(getKey(token));
        }else{
            removeLocalSession(getKey(token));
        }
    }

    public void removeLocalSession(String key){
        caches.remove(key);
    }

    public void removeRedisSession(String key){
        redisUtil.del(key);
    }

    @Override
    public boolean haveSession(String token) {
        if(CACHE_REDIS.equals(cacheType)){
            return haveRedisSession(getKey(token));
        }else{
            return haveLocalSession(getKey(token));
        }
    }

    public boolean haveLocalSession(String key){
        return caches.containsKey(key);
    }

    public boolean haveRedisSession(String key){
        return redisUtil.hasKey(key);
    }

    public String getKey(String token){
        return SESSION_PREFIX + token;
    }
}
