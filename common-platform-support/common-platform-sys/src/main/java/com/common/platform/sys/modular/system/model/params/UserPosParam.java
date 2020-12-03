package com.common.platform.sys.modular.system.model.params;

import com.common.platform.sys.base.pojo.BaseValidatingParam;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserPosParam implements Serializable, BaseValidatingParam {

    /**
     * 主键id
     */
    private Long userPosId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 职位id
     */
    private Long posId;

    @Override
    public String checkParam() {
        return null;
    }
}
