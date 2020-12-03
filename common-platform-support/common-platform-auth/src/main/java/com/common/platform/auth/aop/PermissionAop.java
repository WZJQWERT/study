package com.common.platform.auth.aop;

import com.common.platform.auth.annotation.Permission;
import com.common.platform.auth.exception.PermissionException;
import com.common.platform.auth.service.AuthService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

@Aspect
@Order(200)
public class PermissionAop {

    @Autowired
    private AuthService authService;

    @Pointcut(value = "@annotation(com.common.platform.auth.annotation.Permission)")
    private void cutPermission(){

    }

    @Around("cutPermission()")
    public Object doPermission(ProceedingJoinPoint point) throws Throwable{
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        Permission permission = method.getAnnotation(Permission.class);
        String[] permissions = permission.value();
        if(permissions.length==0){
            boolean result = authService.checkAll();
            if(result){
                return point.proceed();
            }else{
                throw new PermissionException();
            }
        }else{
            boolean result = authService.check(permissions);
            if(result){
                return point.proceed();
            }else{
                throw new PermissionException();
            }
        }
    }

}
