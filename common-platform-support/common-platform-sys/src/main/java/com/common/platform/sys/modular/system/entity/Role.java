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
 * 角色表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role")
public class Role extends BaseEntity {

    /**
     * 主键id
     */
    @TableId(value = "role_id",type = IdType.ASSIGN_ID)
    private Long roleId;

    /**
     * 父角色id
     */
    @TableField("pid")
    private Long pid;

    /**
     * 角色名称
     */
    @TableField("name")
    private String name;

    /**
     * 提示
     */
    @TableField("description")
    private String description;

    /**
     * 序号
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 乐观锁
     */
    @TableField("version")
    private Integer version;

}
