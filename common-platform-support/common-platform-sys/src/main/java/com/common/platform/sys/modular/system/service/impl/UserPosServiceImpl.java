package com.common.platform.sys.modular.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.platform.base.page.LayuiPageInfo;
import com.common.platform.base.page.factory.LayuiPageFactory;
import com.common.platform.base.utils.CoreUtil;
import com.common.platform.sys.modular.system.entity.UserPos;
import com.common.platform.sys.modular.system.mapper.UserPosMapper;
import com.common.platform.sys.modular.system.model.params.UserPosParam;
import com.common.platform.sys.modular.system.service.UserPosService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 用户职位关联表 服务实现类
 * </p>
 */
@Service
public class UserPosServiceImpl extends ServiceImpl<UserPosMapper, UserPos> implements UserPosService {

    @Override
    public void add(UserPosParam param) {
        UserPos entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(UserPosParam param) {
        this.removeById(getKey(param));
    }

    @Override
    public void update(UserPosParam param) {
        UserPos oldEntity = getOldEntity(param);
        UserPos newEntity = getEntity(param);
        CoreUtil.copyProperties(newEntity,oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public LayuiPageInfo findPageBySpec(UserPosParam param) {
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext,param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private UserPos getEntity(UserPosParam param){
        UserPos entity = new UserPos();
        CoreUtil.copyProperties(param,entity);
        return entity;
    }

    private Serializable getKey(UserPosParam param){
        return param.getUserPosId();
    }

    private UserPos getOldEntity(UserPosParam param){
        return this.getById(getKey(param));
    }

    private Page getPageContext(){
        return LayuiPageFactory.defaultPage();
    }
}
