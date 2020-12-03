package com.common.platform.sys.modular.system.model.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DictResult implements Serializable {

    /**
     * 字典id
     */
    private Long dictId;

    /**
     * 所属字典类型的id
     */
    private Long dictTypeId;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 上级代码id
     */
    private Long parentId;

    /**
     * 状态（字典）
     */
    private String status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 字典的描述
     */
    private String description;

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

    /**
     * 父级字典名称
     */
    private String parentName;
}
