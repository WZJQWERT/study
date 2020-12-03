package com.common.platform.sys.modular.system.model.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserPosResult implements Serializable {

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
}
