package com.common.platform.sys.modular.system.model.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PositionResult implements Serializable {

    /**
     * 主键id
     */
    private Long positionId;

    /**
     * 职位名称
     */
    private String name;

    /**
     * 职位编码
     */
    private String code;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 状态(字典)
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private Long updateUser;
}
