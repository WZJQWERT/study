package com.common.platform.auth.pojo;

import com.common.platform.base.utils.CoreUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Setter
@Getter
public class LoginUser implements UserDetails,Serializable {

    /**
     * 用户主键ID
     */
    private Long id;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 角色列表
     */
    private List<Long> roleList;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 角色名称列表
     */
    private List<String> roleNames;

    /**
     * 角色备注（code）
     */
    private List<String> roleTips;

    /**
     * 系统标识
     */
    private List<Map<String,Object>> systemTypes;

    /**
     * 拥有的权限
     */
    private Set<String> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(CoreUtil.isNotEmpty(this.roleNames)){
            for (String roleName : this.roleNames){
                GrantedAuthority grantedAuthority = (GrantedAuthority) ()->roleName;
                grantedAuthorities.add(grantedAuthority);
            }
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
