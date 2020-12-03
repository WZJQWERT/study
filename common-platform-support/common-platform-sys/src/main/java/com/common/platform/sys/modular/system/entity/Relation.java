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
 * 角色和菜单关联表
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("sys_relation")
public class Relation implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "relation_id",type = IdType.ASSIGN_ID)
    private Long relationId;

    /**
     * 菜单id
     */
    @TableField("menu_id")
    private Long menuId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;

}
