package com.common.platform.sys.modular.system.service;

import com.common.platform.base.page.LayuiPageInfo;
import com.common.platform.sys.modular.system.entity.UserPos;
import com.baomidou.mybatisplus.extension.service.IService;
import com.common.platform.sys.modular.system.model.params.UserPosParam;

/**
 * <p>
 * 用户职位关联表 服务类
 * </p>
 */
public interface UserPosService extends IService<UserPos> {

    /**
     * 新增
     */
    void add(UserPosParam param);

    /**
     * 删除
     */
    void delete(UserPosParam param);

    /**
     * 修改
     */
    void update(UserPosParam param);

    /**
     * 分页
     */
    LayuiPageInfo findPageBySpec(UserPosParam param);
}
