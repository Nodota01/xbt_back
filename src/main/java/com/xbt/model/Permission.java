package com.xbt.model;

import lombok.Data;
import java.sql.Timestamp;

/**
 * 权限实体类
 */
@Data
public class Permission  extends BaseEntity{
    private Long id; // 权限ID
    private String name; // 权限名称
    private String description; // 权限描述
    private Timestamp createdAt; // 创建时间
    private Timestamp updatedAt; // 更新时间
}