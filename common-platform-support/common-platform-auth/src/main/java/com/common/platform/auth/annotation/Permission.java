package com.common.platform.auth.annotation;

import java.lang.annotation.*;

/**
 * 权限注释 用于检查权限 规定访问权限
 * @example @Permission({role1,role2})
 * @example @Permission
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Permission {

    String[] value() default {};
}
