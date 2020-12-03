package com.common.platform.sys.log;

import com.common.platform.base.config.context.SpringContextHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION)
public class LogObjectHolder implements Serializable {

    private Object object = null;

    public void set(Object obj){
        this.object = obj;
    }

    public Object get(){
        return this.object;
    }

    public static LogObjectHolder me(){
        return SpringContextHolder.getBean(LogObjectHolder.class);
    }
}
