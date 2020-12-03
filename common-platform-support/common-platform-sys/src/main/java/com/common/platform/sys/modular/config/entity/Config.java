package com.common.platform.sys.modular.config.entity;

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
 * 参数配置
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_config")
public class Config extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 属性编码标识
     */
    @TableField("code")
    private String code;

    /**
     * 是否是字典中的值
     */
    @TableField("dict_flag")
    private String dictFlag;

    /**
     * 字典类型的编码
     */
    @TableField("dict_type_id")
    private Long dictTypeId;

    /**
     * 属性值，如果是字典中的类型，则为dict的code
     */
    @TableField("value")
    private String value;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
