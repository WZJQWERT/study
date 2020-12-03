package com.common.platform.sys.modular.system.model.result;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DictTypeResult implements Serializable {

    /**
     * 字典类型id
     */
    private Long dictTypeId;

    /**
     * 字典类型编码
     */
    private String code;

    /**
     * 字典类型名称
     */
    private String name;

    /**
     * 字典描述
     */
    private String description;

    /**
     * 是否是系统字典，Y-是，N-否
     */
    private String systemFlag;

    /**
     * 状态(字典)
     */
    private String status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 添加人
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
