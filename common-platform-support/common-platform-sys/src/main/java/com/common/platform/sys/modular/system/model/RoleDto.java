package com.common.platform.sys.modular.system.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDto implements Serializable {

    /**
     * 主键id
     */
    private Long roleId;

    /**
     * 父角色id
     */
    private Long pid;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 提示
     */
    private String description;

    /**
     * 序号
     */
    private Integer sort;

}
