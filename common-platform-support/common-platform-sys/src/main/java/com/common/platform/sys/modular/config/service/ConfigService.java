package com.common.platform.sys.modular.config.service;

import com.common.platform.base.page.LayuiPageInfo;
import com.common.platform.sys.modular.config.entity.Config;
import com.baomidou.mybatisplus.extension.service.IService;
import com.common.platform.sys.modular.config.model.params.ConfigParam;

/**
 * <p>
 * 参数配置 服务类
 * </p>
 */
public interface ConfigService extends IService<Config> {

    /**
     * 新增
     */
    void add(ConfigParam param);

    /**
     * 删除
     */
    void delete(ConfigParam param);

    /**
     * 更新
     */
    void update(ConfigParam param);

    /**
     * 分页
     */
    LayuiPageInfo findPageBySpec(ConfigParam param);
}
