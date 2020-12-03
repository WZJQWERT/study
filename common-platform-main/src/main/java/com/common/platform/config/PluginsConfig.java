package com.common.platform.config;

import com.common.platform.auth.context.LoginContextHolder;
import com.common.platform.base.config.database.AutoFullHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(basePackages = {"com.common.platform.sys.modular.*.mapper"})
@EnableTransactionManagement(proxyTargetClass = true)
public class PluginsConfig {

    @Bean
    public AutoFullHandler mybatisPlusFieldHandler(){
        return new AutoFullHandler(){
            @Override
            protected Object getUserUniqueId() {
                try{
                    return LoginContextHolder.getContext().getUser().getId();
                }catch (Exception e){
                    return -100L;
                }
            }
        };
    }
}
