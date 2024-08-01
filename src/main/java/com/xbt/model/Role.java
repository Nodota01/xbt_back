package com.xbt.model;

import lombok.Data;
import java.sql.Timestamp;

/**
 * 角色实体类
 */
@Data
public class Role  extends BaseEntity{
    private Long id; // 角色ID
    private String name; // 角色名称
    private String description; // 角色描述
    private Timestamp createdAt; // 创建时间
    private Timestamp updatedAt; // 更新时间
}