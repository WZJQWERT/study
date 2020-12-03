package com.common.platform.sys.modular.config.init;

import com.common.platform.base.consts.ConstantsContext;
import com.common.platform.sys.modular.config.entity.Config;
import com.common.platform.sys.modular.config.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SysConfigInit implements CommandLineRunner {

    @Autowired
    private ConfigService configService;

    @Override
    public void run(String... args) throws Exception {
        List<Config> list = this.configService.list();
        if(list!=null && list.size()>0){
            for (Config config : list){
                ConstantsContext.putConstant(config.getCode(),config.getValue());
            }
            log.info("初始化常量：" + list.size() + "条！");
        }
    }
}
