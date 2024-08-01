package com.xbt.model;

import lombok.Data;
import java.sql.Timestamp;

/**
 * 菜单实体类
 */
@Data
public class Menu extends BaseEntity {
    private Long id;           // 菜单ID
    private String name;       // 菜单名称
    private String path;
}