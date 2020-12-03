package com.common.platform.config;

import com.common.platform.sys.beetl.BeetlProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {

    /**
     * beetl的模板配置
     */
    @Bean
    @ConfigurationProperties(prefix = BeetlProperties.BEETLCONF_PREFIX)
    public BeetlProperties beetlProperties(){
        return new BeetlProperties();
    }
}
