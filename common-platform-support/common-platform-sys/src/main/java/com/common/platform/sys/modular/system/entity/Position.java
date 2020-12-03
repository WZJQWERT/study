package com.common.platform.sys.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.common.platform.sys.base.pojo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 职位表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_position")
public class Position extends BaseEntity {

    /**
     * 主键id
     */
    @TableId(value = "position_id",type = IdType.ASSIGN_ID)
    private Long positionId;

    /**
     * 职位名称
     */
    @TableField("name")
    private String name;

    /**
     * 职位编码
     */
    @TableField("code")
    private String code;

    /**
     * 顺序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 状态(字典)
     */
    @TableField("status")
    private String status;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
