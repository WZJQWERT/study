package com.common.platform.sys.modular.system.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.platform.base.enums.CommonStatus;
import com.common.platform.base.exception.BizExceptionEnum;
import com.common.platform.base.exception.ServiceException;
import com.common.platform.base.page.LayuiPageInfo;
import com.common.platform.base.tree.node.ZTreeNode;
import com.common.platform.base.utils.CoreUtil;
import com.common.platform.sys.exception.RequestEmptyException;
import com.common.platform.sys.modular.system.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.common.platform.sys.modular.system.entity.DictType;
import com.common.platform.sys.modular.system.mapper.DictMapper;
import com.common.platform.sys.modular.system.model.params.DictParam;
import com.common.platform.sys.modular.system.model.result.DictResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基础字典 服务类
 * </p>
 */
@Service
public class DictService extends ServiceImpl<DictMapper, Dict> {

    @Autowired
    private DictTypeService dictTypeService;

    /**
     * 新增
     */
    public void add(DictParam param) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.and(i -> i.eq("code", param.getCode()).or().eq("name", param.getName()))
                .and(i -> i.eq("dict_type_id", param.getDictTypeId()));
        List<Dict> list = this.list(dictQueryWrapper);
        if (list != null && list.size() > 0) {
            throw new ServiceException(BizExceptionEnum.DICT_EXISTED);
        }
        Dict dict = getEntity(param);
        dictSetPids(dict);
        dict.setStatus(CommonStatus.ENABLE.getCode());
        this.save(dict);
    }

    /**
     * 删除
     */
    public void delete(DictParam param){
        List<Long> subIds = getSubIds(param.getDictId());
        if(subIds.size()>0){
            this.removeByIds(subIds);
        }
        this.removeById(getKey(param));
    }

    /**
     * 更新
     */
    public void update(DictParam param){
        Dict oldEntity = getOldEntity(param);
        Dict newEntity = getEntity(param);

        CoreUtil.copyProperties(newEntity,oldEntity);

        QueryWrapper<Dict> wrapper = new QueryWrapper<Dict>()
                .and(i->i.eq("code",newEntity.getCode()).or().eq("name",newEntity.getName()))
                .and(i->i.ne("dict_id",newEntity.getDictId()))
                .and(i->i.eq("dict_type_id",param.getDictTypeId()));
        int dicts = this.count(wrapper);
        if(dicts>0){
            throw new ServiceException(BizExceptionEnum.DICT_EXISTED);
        }
        dictSetPids(newEntity);
        this.updateById(newEntity);
    }

    /**
     * 查询分页数据
     */
    public LayuiPageInfo findPageBySpec(DictParam param){
        QueryWrapper<Dict> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("dict_type_id",param.getDictTypeId());
        if(CoreUtil.isNotEmpty(param.getCondition())){
            objectQueryWrapper.and(i->i.eq("code",param.getCode()).or().eq("name",param.getName()));
        }
        objectQueryWrapper.orderByAsc("sort");

        List<Dict> list = this.list(objectQueryWrapper);

        Dict dict = new Dict();
        dict.setName("根节点");
        dict.setDictId(0L);
        dict.setParentId(-999L);
        list.add(dict);

        LayuiPageInfo result = new LayuiPageInfo();
        result.setData(list);
        return result;
    }

    /**
     * 获取字典的树形列表（ZTree）
     */
    public List<ZTreeNode> dictTreeList(Long dictTypeId,Long dictId){
        if(dictTypeId==null){
            throw new RequestEmptyException();
        }
        List<ZTreeNode> tree = this.baseMapper.dictTree(dictTypeId);
        List<Long> subIds = getSubIds(dictId);
        List<ZTreeNode> resultTree = new ArrayList<>();
        for (ZTreeNode zTreeNode:tree){
            if(CoreUtil.isNotEmpty(dictId) && dictId.equals(zTreeNode.getId())){
                continue;
            }
            if(subIds.contains(zTreeNode.getId())){
                continue;
            }
            resultTree.add(zTreeNode);
        }
        resultTree.add(ZTreeNode.createParent());
        return resultTree;
    }

    /**
     * 查看dict的详情
     */
    public DictResult dictDetail(Long dictId){
        if(CoreUtil.isEmpty(dictId)){
            throw new RequestEmptyException();
        }
        DictResult dictResult = new DictResult();
        Dict detail = this.getById(dictId);
        if(detail==null){
            throw new RequestEmptyException();
        }
        if(CoreUtil.isNotEmpty(detail.getParentId())){
            Long parentId = detail.getParentId();
            Dict dictType = this.getById(parentId);
            if(dictType!=null){
                dictResult.setParentName(dictType.getName());
            }else{
                dictResult.setParentName("无父级");
            }
        }
        CoreUtil.copyProperties(detail,dictResult);
        return dictResult;
    }

    /**
     * 查询字典列表，通过字典类型
     */
    public List<Dict> listDicts(Long dictTypeId){
        QueryWrapper<Dict> objectQueryWrapper= new QueryWrapper<>();
        objectQueryWrapper.eq("dict_type_id",dictTypeId);
        List<Dict> list = this.list(objectQueryWrapper);
        if(list==null){
            return new ArrayList<>();
        }else{
            return list;
        }
    }

    /**
     * 查询字典列表，通过字典类型code
     */
    public List<Dict> listDictsByCode(String dictTypeCode){
        QueryWrapper<DictType> wrapper = new QueryWrapper<>();
        wrapper.eq("code",dictTypeCode);
        DictType one = this.dictTypeService.getOne(wrapper);
        return listDicts(one.getDictTypeId());
    }

    /**
     * 查询字典列表，通过字典code
     */
    public List<Map<String,Object>> getDictsByCodes(List<String> dictCodes){
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.in("code",dictCodes).orderByAsc("sort");
        ArrayList<Map<String,Object>> results= new ArrayList<>();
        List<Dict> list = this.list(wrapper);
        for (Dict dict:list){
            Map<String,Object> map = BeanUtil.beanToMap(dict);
            results.add(map);
        }
        return results;
    }

    private Dict getOldEntity(DictParam param){
        return this.getById(getKey(param));
    }

    private Serializable getKey(DictParam param){
        return param.getDictId();
    }

    private List<Long> getSubIds(Long dictId){
        ArrayList<Long> longs = new ArrayList<>();
        if(CoreUtil.isEmpty(dictId)){
            return longs;
        }else{
            List<Dict> list = this.baseMapper.likeParentIds(dictId);
            for (Dict dict:list){
                longs.add(dict.getDictId());
            }
            return longs;
        }
    }

    private void dictSetPids(Dict param) {
        if (param.getParentId().equals(0L)) {
            param.setParentIds("[0]");
        } else {
            Long parentId = param.getParentId();
            Dict parent = this.getById(parentId);
            if (parent == null) {
                param.setParentIds("[0]");
            } else {
                param.setParentIds(parent.getParentIds() + "," + "[" + parentId + "]");
            }
        }
    }

    private Dict getEntity(DictParam param) {
        Dict entity = new Dict();
        CoreUtil.copyProperties(param, entity);
        return entity;
    }
}
