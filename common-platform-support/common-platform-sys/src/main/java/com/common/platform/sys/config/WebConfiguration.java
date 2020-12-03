package com.common.platform.sys.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.BeanTypeAutoProxyCreator;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.common.platform.base.config.context.SpringContextHolder;
import com.common.platform.sys.exception.page.ErrorView;
import com.common.platform.sys.filter.XssFilter;
import com.common.platform.sys.listener.ConfigListener;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 配置string解析器，放置在json解析器之前
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        HttpMessageConverter<?> httpMessageConverter = converters.get(0);
        converters.remove(0);
        converters.add(2,httpMessageConverter);
    }

    /**
     * 静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
    }

    /**
     * 默认错误页面
     */
    @Bean("error")
    public ErrorView error(){
        return new ErrorView();
    }

    /**
     * RequestContextListener
     */
    @Bean
    public ServletListenerRegistrationBean<RequestContextListener> requestContextListenerServletListenerRegistrationBean(){
        return new ServletListenerRegistrationBean<>(new RequestContextListener());
    }

    /**
     * ConfigListener
     */
    @Bean
    public ServletListenerRegistrationBean<ConfigListener> configListenerServletListenerRegistrationBean(){
        return new ServletListenerRegistrationBean<>(new ConfigListener());
    }

    @Bean
    public SpringContextHolder springContextHolder(){
        return new SpringContextHolder();
    }

    @Bean
    public FilterRegistrationBean xssFilterRegistration(){
        XssFilter xssFilter = new XssFilter();
        xssFilter.setUrlExclusion(Arrays.asList("/notice/add","/notice/update"));
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(xssFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public DefaultKaptcha kaptcha(){
        Properties properties = new Properties();
        properties.put("kaptcha.border","no");
        properties.put("kaptcha.border.color","105,179,90");
        properties.put("kaptcha.textproducer.font.color","blue");
        properties.put("kaptcha.image.width","125");
        properties.put("kaptcha.image.height","45");
        properties.put("kaptcha.textproducer.font.size","45");
        properties.put("kaptcha.session.key","code");
        properties.put("kaptcha.textproducer.char.length","4");
        properties.put("kaptcha.textproducer.font.names","宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    /**
     * druidServlet 注册
     */
    @Bean
    public ServletRegistrationBean druidServletRegistration(){
        ServletRegistrationBean registration = new ServletRegistrationBean(new StatViewServlet());
        registration.addUrlMappings("/druid/*");
        return registration;
    }

    /**
     * druid监控 配置URI拦截策略
     */
    @Bean
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions",
                "/assets/*,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid,/druid/*,/static/*");
        filterRegistrationBean.addInitParameter("principalSessionName","username");
        return filterRegistrationBean;
    }

    /**
     * druid数据库连接池监控
     */
    @Bean
    public DruidStatInterceptor druidStatInterceptor(){
        return new DruidStatInterceptor();
    }

    @Bean
    public JdkRegexpMethodPointcut druidStatPointcut(){
        JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
        String patterns = "com.common.platform.sys.modular.*service.*";
        druidStatPointcut.setPatterns(patterns);
        return druidStatPointcut;
    }

    @Bean
    public BeanTypeAutoProxyCreator beanTypeAutoProxyCreator(){
        BeanTypeAutoProxyCreator beanTypeAutoProxyCreator = new BeanTypeAutoProxyCreator();
        beanTypeAutoProxyCreator.setTargetBeanType(DruidDataSource.class);
        beanTypeAutoProxyCreator.setInterceptorNames("druidStatInterceptor");
        return beanTypeAutoProxyCreator;
    }

    @Bean
    public Advisor druidStatAdvisor(){
        return new DefaultPointcutAdvisor(druidStatPointcut(),druidStatInterceptor());
    }

}
