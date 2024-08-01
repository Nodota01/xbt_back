package com.xbt.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 用户模型类
 */
@Data
public class User  extends BaseEntity implements UserDetails {
     Long id;

     @NotBlank(message = "username cannot be null")
     String username;
     String password;

     String captcha;
     List<Role> roles;

     String role;

     Set<Menu> menus;

    private String email;            // 电子邮件
    private String phoneNumber;      // 电话号码
    private String name;             // 姓名
    private String gender;           // 性别
    private String userNumber;       // 用户编号
    private Integer status;          // 用户状态（1: 激活，0: 未激活）
    private Boolean isDeleted;       // 逻辑删除标志位（0: 未删除，1: 已删除）


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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