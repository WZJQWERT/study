package com.common.platform.sys.modular.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.common.platform.sys.base.pojo.BaseEntity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 登录记录
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("sys_login_log")
public class LoginLog implements Serializable{

    /**
     * 主键
     */
    @TableId(value = "login_log_id",type = IdType.ASSIGN_ID)
    private Long loginLogId;

    /**
     * 日志名称
     */
    @TableField("log_name")
    private String logName;

    /**
     * 管理员id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 是否执行成功
     */
    @TableField("succeed")
    private String succeed;

    /**
     * 具体消息
     */
    @TableField("message")
    private String message;

    /**
     * 登录ip
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    public Date createTime;

}
