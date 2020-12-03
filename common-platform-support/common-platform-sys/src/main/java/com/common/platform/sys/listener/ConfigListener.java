package com.common.platform.sys.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;

public class ConfigListener implements ServletContextListener {

    private static Map<String,String> conf = new HashMap<>();
    public static Map<String,String> getConf(){
        return conf;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        //项目发布，当前运行环境的绝对路径
        conf.put("realPath",sc.getRealPath("/").replaceFirst("/",""));

        conf.put("contextPath",sc.getContextPath());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
         conf.clear();
    }
}
