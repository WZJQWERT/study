package com.common.platform.sys.modular.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.platform.sys.modular.system.entity.UserPos;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.common.platform.sys.modular.system.model.params.UserPosParam;
import com.common.platform.sys.modular.system.model.result.UserPosResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户职位关联表 Mapper 接口
 * </p>
 */
public interface UserPosMapper extends BaseMapper<UserPos> {

    /**
     * 获取列表
     */
    List<UserPosResult> customList(@Param("paramCondition")UserPosParam paramCondition);

    /**
     * 获取map列表
     */
    List<Map<String,Object>> customMapList(@Param("paramCondition")UserPosParam paramCondition);

    /**
     * 获取分页列表
     */
    Page<UserPosResult> customPageList(@Param("page") Page page,@Param("paramCondition")UserPosParam paramCondition);

    /**
     * 获取map分页列表
     */
    Page<Map<String,Object>> customPageMapList(@Param("page") Page page,@Param("paramCondition")UserPosParam paramCondition);
}
