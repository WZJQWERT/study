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
 * 操作日志
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("sys_operation_log")
public class OperationLog implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "operation_log_id",type = IdType.ASSIGN_ID)
    private Long operationLogId;

    /**
     * 日志类型(字典)
     */
    @TableField("log_type")
    private String logType;

    /**
     * 日志名称
     */
    @TableField("log_name")
    private String logName;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 类名称
     */
    @TableField("class_name")
    private String className;

    /**
     * 方法名称
     */
    @TableField("method")
    private String method;

    /**
     * 是否成功(字典)
     */
    @TableField("succeed")
    private String succeed;

    /**
     * 备注
     */
    @TableField("message")
    private String message;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    public Date createTime;

}
