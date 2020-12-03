package com.common.platform.sys.modular.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.platform.base.config.database.DataScope;
import com.common.platform.sys.modular.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 修改用户状态
     */
    int setStatus(@Param("userId") Long userId, @Param("status") String status);

    /**
     * 修改密码
     */
    int changePwd(@Param("userId") Long userId, @Param("pwd") String pwd);

    /**
     * 根据查询条件查询用户列表(部门)
     */
    Page<Map<String, Object>> selectUsers(@Param("page") Page page,
                                          @Param("dataScope") DataScope dataScope,
                                          @Param("name") String name,
                                          @Param("beginTime") String beginTime,
                                          @Param("endTime") String endTime,
                                          @Param("deptId") Long deptId);

    /**
     * 根据查询条件查询用户列表（角色）
     */
    Page<Map<String, Object>> selectUsersByRole(@Param("page") Page page,
                                                @Param("name") String name,
                                                @Param("beginTime") String beginTime,
                                                @Param("endTime") String endTime,
                                                @Param("roleId") Long roleId);

    /**
     * 设置用户的角色
     */
    int setRoles(@Param("userId") Long userId, @Param("roleIds") String roleIds);

    /**
     * 通过账号获取用户
     */
    User getByAccount(@Param("account") String account);

    /**
     * 选择办理人
     */
    IPage<Map<String,Object>> listUserAndRoleExpectAdmin(Page page);
}
