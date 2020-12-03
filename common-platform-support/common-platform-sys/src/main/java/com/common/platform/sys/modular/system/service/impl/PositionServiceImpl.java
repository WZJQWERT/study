package com.common.platform.sys.modular.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.platform.base.page.LayuiPageInfo;
import com.common.platform.base.page.factory.LayuiPageFactory;
import com.common.platform.base.utils.CoreUtil;
import com.common.platform.sys.modular.system.entity.Position;
import com.common.platform.sys.modular.system.entity.UserPos;
import com.common.platform.sys.modular.system.mapper.PositionMapper;
import com.common.platform.sys.modular.system.model.params.PositionParam;
import com.common.platform.sys.modular.system.service.PositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.platform.sys.modular.system.service.UserPosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职位表 服务实现类
 * </p>
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {

    @Autowired
    private UserPosService userPosService;

    @Override
    public void add(PositionParam param) {
        Position entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(PositionParam param) {
        this.removeById(getKey(param));
    }

    @Override
    public void update(PositionParam param) {
        Position oldEntity = getOldEntity(param);
        Position newEntity = getEntity(param);

        CoreUtil.copyProperties(newEntity,oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public LayuiPageInfo findPageBySpec(PositionParam param) {
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext,param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public LayuiPageInfo listPositions(Long userId) {
        List<Map<String,Object>> list = this.baseMapper.getAllPositionMap();

        if(userId == null){

        }else{
            List<UserPos> userPosList = this.userPosService.list(new QueryWrapper<UserPos>().eq("user_id",userId));
            if(userPosList!=null && userPosList.size()>0){
                for (UserPos userPos: userPosList){
                    for (Map<String,Object> positionMap : list){
                        if(userPos.getPosId().equals(positionMap.get("positionId"))){
                            positionMap.put("selected",true);
                        }
                    }
                }
            }
        }
        LayuiPageInfo layuiPageInfo = new LayuiPageInfo();
        layuiPageInfo.setData(list);
        return layuiPageInfo;
    }

    private Position getEntity(PositionParam param) {
        Position entity = new Position();
        CoreUtil.copyProperties(param, entity);
        return entity;
    }

    private Serializable getKey(PositionParam param){
        return param.getPositionId();
    }

    private Position getOldEntity(PositionParam param){
        return this.getById(getKey(param));
    }

    private Page getPageContext(){
        return LayuiPageFactory.defaultPage();
    }
}
