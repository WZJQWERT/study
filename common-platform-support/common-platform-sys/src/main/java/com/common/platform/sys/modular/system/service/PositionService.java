package com.common.platform.sys.modular.system.service;

import com.common.platform.base.page.LayuiPageInfo;
import com.common.platform.sys.modular.system.entity.Position;
import com.baomidou.mybatisplus.extension.service.IService;
import com.common.platform.sys.modular.system.model.params.PositionParam;

/**
 * <p>
 * 职位表 服务类
 * </p>
 */
public interface PositionService extends IService<Position> {

    /**
     * 新增
     */
    void add(PositionParam param);

    /**
     * 删除
     */
    void delete(PositionParam param);

    /**
     * 修改
     */
    void update(PositionParam param);

    /**
     * 分页
     */
    LayuiPageInfo findPageBySpec(PositionParam param);

    /**
     * 获取用户的职位列表
     */
    LayuiPageInfo listPositions(Long userId);
}
