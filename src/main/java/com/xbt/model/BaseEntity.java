package com.xbt.model;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 基础实体类，包含公共字段
 */
@Data
public class BaseEntity {
    private Boolean isDeleted;       // 逻辑删除标志位（0: 未删除，1: 已删除）
    private String createdBy;        // 创建人
    private String updatedBy;        // 修改人
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}