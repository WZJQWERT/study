package com.common.platform.sys.modular.system.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDto implements Serializable {
    /**
     * 主键id
     */
    private Long userId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 名字
     */
    private String name;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 性别(字典)
     */
    private String sex;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 角色id(多个逗号隔开)
     */
    private String roleId;

    /**
     * 部门id(多个逗号隔开)
     */
    private Long deptId;

    /**
     * 状态(字典)
     */
    private String status;

    /**
     * 职位
     */
    private String position;

}
