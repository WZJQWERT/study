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
 * 用户职位关联表
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("sys_user_pos")
public class UserPos implements Serializable {

    /**
     * 主键id
     */
    @TableId(value = "user_pos_id", type = IdType.ASSIGN_ID)
    private Long userPosId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 职位id
     */
    @TableField("pos_id")
    private Long posId;

}
