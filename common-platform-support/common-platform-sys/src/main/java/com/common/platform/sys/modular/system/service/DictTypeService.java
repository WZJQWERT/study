package com.common.platform.sys.modular.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.platform.base.enums.CommonStatus;
import com.common.platform.base.exception.BizExceptionEnum;
import com.common.platform.base.exception.ServiceException;
import com.common.platform.base.page.LayuiPageInfo;
import com.common.platform.base.page.factory.LayuiPageFactory;
import com.common.platform.base.utils.CoreUtil;
import com.common.platform.sys.exception.RequestEmptyException;
import com.common.platform.sys.modular.system.entity.Dict;
import com.common.platform.sys.modular.system.entity.DictType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.common.platform.sys.modular.system.mapper.DictTypeMapper;
import com.common.platform.sys.modular.system.model.params.DictTypeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 */
@Service
public class DictTypeService extends ServiceImpl<DictTypeMapper, DictType> {

    @Autowired
    private DictService dictService;

    /**
     * 新增
     */
    public void add(DictTypeParam param){
        QueryWrapper<DictType> dictTypeQueryWrapper = new QueryWrapper<>();
        dictTypeQueryWrapper.eq("code", param.getCode()).or().eq("name",param.getName());
        List<DictType> list = this.list(dictTypeQueryWrapper);
        if(list!=null && list.size()>0){
            throw new ServiceException(BizExceptionEnum.DICT_EXISTED);
        }
        DictType entity = getEntity(param);

        entity.setStatus(CommonStatus.ENABLE.getCode());
        this.save(entity);
    }

    /**
     * 删除
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(DictTypeParam param){

        if(param==null || param.getDictTypeId()==null){
            throw new RequestEmptyException("字典类型id为空");
        }
        this.removeById(getKey(param));

        this.dictService.remove(new QueryWrapper<Dict>().eq("dict_type_id",getKey(param)));
    }

    /**
     *更新
     */
    public void update(DictTypeParam param){
        DictType oldEntity = getOldEntity(param);
        DictType newEntity = getEntity(param);
        CoreUtil.copyProperties(newEntity,oldEntity);

        QueryWrapper<DictType> wrapper = new QueryWrapper<>();
        wrapper.and(i->i.eq("code",newEntity.getCode()).or().eq("name",newEntity.getName()))
        .and(i->i.ne("dict_type_id",newEntity.getDictTypeId()));
        int dicts = this.count(wrapper);

        if(dicts>0){
            throw new ServiceException(BizExceptionEnum.DICT_EXISTED);
        }
        this.updateById(newEntity);
    }

    /**
     * 查询分页数据
     */
    public LayuiPageInfo findPageBySpec(DictTypeParam param){
        Page pageContext = getPageContext();
        QueryWrapper<DictType> objectQueryWrapper = new QueryWrapper<>();
        if(CoreUtil.isNotEmpty(param.getCondition())){
            objectQueryWrapper.and(i->i.eq("code",param.getCondition()).or().eq("name",param.getName()));
        }
        if(CoreUtil.isNotEmpty(param.getStatus())){
            objectQueryWrapper.and(i->i.eq("status",param.getStatus()));
        }
        if(CoreUtil.isNotEmpty(param.getSystemFlag())){
            objectQueryWrapper.and(i->i.eq("system_flag",param.getSystemFlag()));
        }
        pageContext.addOrder(new OrderItem().setColumn("sort").setAsc(true));
        IPage page = this.page(pageContext,objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Page getPageContext(){
        return LayuiPageFactory.defaultPage();
    }

    private DictType getOldEntity(DictTypeParam param){
        return this.getById(getKey(param));
    }

    private Serializable getKey(DictTypeParam param){
        return param.getDictTypeId();
    }

    private DictType getEntity(DictTypeParam param){
        DictType entity = new DictType();
        CoreUtil.copyProperties(param,entity);
        return entity;
    }

}
