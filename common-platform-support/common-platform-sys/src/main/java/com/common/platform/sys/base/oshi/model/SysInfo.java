package com.common.platform.sys.base.oshi.model;

import lombok.Data;

/**
 * 系统相关
 */
@Data
public class SysInfo {

    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器IP
     */
    private String computerIp;

    /**
     * 项目路径
     */
    private String userDir;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;
}
