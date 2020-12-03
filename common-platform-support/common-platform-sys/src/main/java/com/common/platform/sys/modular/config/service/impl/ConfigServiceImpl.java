package com.common.platform.sys.modular.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.platform.base.consts.ConfigConstant;
import com.common.platform.base.consts.ConstantsContext;
import com.common.platform.base.exception.BizExceptionEnum;
import com.common.platform.base.exception.ServiceException;
import com.common.platform.base.page.LayuiPageInfo;
import com.common.platform.base.page.factory.LayuiPageFactory;
import com.common.platform.base.utils.CoreUtil;
import com.common.platform.sys.modular.config.entity.Config;
import com.common.platform.sys.modular.config.mapper.ConfigMapper;
import com.common.platform.sys.modular.config.model.params.ConfigParam;
import com.common.platform.sys.modular.config.service.ConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 参数配置 服务实现类
 * </p>
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Override
    public void add(ConfigParam param) {
        Config entity = getEntity(param);
        Config config = new Config();
        config.setCode(entity.getCode());
        List<Config> list = this.list(new QueryWrapper<>(config));
        if(list!=null && list.size()>0){
            throw new ServiceException(BizExceptionEnum.ALREADY_CONSTANTS_ERROR);
        }
        if(CoreUtil.isNotEmpty(param.getDictFlag()) && param.getDictFlag().equalsIgnoreCase("Y")){
            entity.setValue(param.getDictValue());
        }
        ConstantsContext.putConstant(entity.getCode(),entity.getValue());

        this.save(entity);
    }

    @Override
    public void delete(ConfigParam param) {
        Config config = this.getById(param.getId());
        if(config!=null && config.getCode().startsWith(ConfigConstant.SYSTEM_CONSTANT_PREFIX)){
            throw new ServiceException(BizExceptionEnum.SYSTEM_CONSTANT_ERROR);
        }
        ConstantsContext.deleteConstant(config.getCode());
        this.removeById(getKey(param));
    }

    @Override
    public void update(ConfigParam param) {
        Config oldEntity = getOldEntity(param);
        Config newEntity = getEntity(param);
        CoreUtil.copyProperties(newEntity,oldEntity);

        if(CoreUtil.isNotEmpty(param.getDictFlag())
         && param.getDictFlag().equalsIgnoreCase("Y")){
            newEntity.setValue(param.getDictValue());
        }else{
            newEntity.setDictFlag("N");
        }
        ConstantsContext.putConstant(newEntity.getCode(),newEntity.getValue());

        this.updateById(newEntity);
    }

    @Override
    public LayuiPageInfo findPageBySpec(ConfigParam param) {
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext,param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Page getPageContext(){
        return LayuiPageFactory.defaultPage();
    }

    private Config getOldEntity(ConfigParam param){
        return this.getById(getKey(param));
    }

    private Serializable getKey(ConfigParam param){
        return param.getId();
    }

    private Config getEntity(ConfigParam param){
        Config entity = new Config();
        CoreUtil.copyProperties(param,entity);
        return entity;
    }
}
