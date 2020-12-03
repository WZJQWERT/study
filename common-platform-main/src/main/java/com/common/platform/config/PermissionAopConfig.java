package com.common.platform.config;

import com.common.platform.auth.aop.PermissionAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionAopConfig {

    @Bean
    public PermissionAop permissionAop(){
        return new PermissionAop();
    }
}
