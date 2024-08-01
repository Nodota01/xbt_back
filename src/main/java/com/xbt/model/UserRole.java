package com.xbt.model;

import lombok.Data;

/**
 * 用户角色关联实体类
 */
@Data
public class UserRole  extends BaseEntity{
    private Long userId; // 用户ID
    private Long roleId; // 角色ID
}