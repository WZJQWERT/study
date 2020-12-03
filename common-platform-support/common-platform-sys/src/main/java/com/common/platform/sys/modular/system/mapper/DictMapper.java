package com.common.platform.sys.modular.system.mapper;

import com.common.platform.base.tree.node.ZTreeNode;
import com.common.platform.sys.modular.system.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 基础字典 Mapper 接口
 * </p>
 */
public interface DictMapper extends BaseMapper<Dict> {

    List<ZTreeNode> dictTree(@Param("dictTypeId") Long dictTypeId);

    List<Dict> likeParentIds(@Param("dictId") Long dictId);
}
