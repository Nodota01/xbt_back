package com.xbt.model;

import lombok.Data;

/**
 * 角色菜单关联实体类
 */
@Data
public class RoleMenu  extends BaseEntity{
    private Long roleId; // 角色ID
    private Long menuId; // 菜单ID
}
