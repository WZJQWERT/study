package com.common.platform.sys.modular.system.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadResult implements Serializable {

    /**
     * 文件唯一id
     */
    private String fileId;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件原始名称
     */
    private String originalFilename;

    /**
     * 文件最终名称
     */
    private String finalName;

    /**
     * 最终文件存储路径
     */
    private String fileSavePath;
}
