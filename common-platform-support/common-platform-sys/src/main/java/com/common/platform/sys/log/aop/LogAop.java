package com.common.platform.sys.log.aop;

import com.common.platform.auth.context.LoginContextHolder;
import com.common.platform.auth.pojo.LoginUser;
import com.common.platform.base.config.context.HttpContext;
import com.common.platform.base.dict.AbstractDictMap;
import com.common.platform.base.log.BussinessLog;
import com.common.platform.sys.log.LogManager;
import com.common.platform.sys.log.LogObjectHolder;
import com.common.platform.sys.log.factory.LogTaskFactory;
import com.common.platform.sys.util.Contrast;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
public class LogAop {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value = "@annotation(com.common.platform.base.log.BussinessLog)")
    public void cutService(){

    }

    @Around("cutService()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable{
        Object result = point.proceed();
        try {
            handle(point);
        }catch (Exception e){
            logger.error("日志记录异常",e);
        }
        return result;
    }

    private void handle(ProceedingJoinPoint point) throws Exception{
        Signature signature = point.getSignature();
        MethodSignature methodSignature = null;
        if(!(signature instanceof MethodSignature)){
            throw new IllegalArgumentException("该注释只能用于方法");
        }
        methodSignature = (MethodSignature) signature;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(),methodSignature.getParameterTypes());

        String methodName = currentMethod.getName();

        LoginUser user = LoginContextHolder.getContext().getUser();
        if(user==null){
            return;
        }
        String className = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();

        BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
        String bussinessName = annotation.value();
        String key = annotation.key();
        Class dictClass = annotation.dict();

        StringBuilder sb = new StringBuilder();
        for(Object param : params){
            sb.append(param).append(" & ");
        }

        String msg;
        if(bussinessName.contains("修改") || bussinessName.contains("编辑")){
            Object obj1 = LogObjectHolder.me().get();
            Map<String ,String> obj2 = HttpContext.getRequestParameters();
            msg = Contrast.contrastObj(dictClass,key,obj1,obj2);
        }else{
            Map<String,String> parameters = HttpContext.getRequestParameters();
            AbstractDictMap dictMap = (AbstractDictMap) dictClass.newInstance();
            msg = Contrast.parseMultiKey(dictMap,key,parameters);
        }
        LogManager.me().executeLog(LogTaskFactory.bussinessLog(user.getId(),bussinessName,className,methodName,msg));
    }
}
