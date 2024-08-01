package com.xbt.model;

import lombok.Data;

/**
 * 角色权限关联实体类
 */
@Data
public class RolePermission  extends BaseEntity{
    private Long roleId; // 角色ID
    private Long permissionId; // 权限ID
}