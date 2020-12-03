package com.common.platform.sys.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.common.platform.sys.base.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件信息表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_file_info")
public class FileInfo extends BaseEntity {

    /**
     * 主键id
     */
    @TableId(value = "file_id",type = IdType.ASSIGN_ID)
    private String fileId;

    /**
     * 文件仓库（oss仓库）
     */
    @TableField("file_bucket")
    private String fileBucket;

    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件后缀
     */
    @TableField("file_suffix")
    private String fileSuffix;

    /**
     * 文件大小kb
     */
    @TableField("file_size_kb")
    private Long fileSizeKb;

    /**
     * 文件唯一标识id
     */
    @TableField("final_name")
    private String finalName;

    /**
     * 存储路径
     */
    @TableField("file_path")
    private String filePath;

}
