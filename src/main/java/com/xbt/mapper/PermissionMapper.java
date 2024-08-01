package com.xbt.mapper;

import com.xbt.model.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限Mapper接口
 */
@Mapper
public interface PermissionMapper {
    void insert(Permission permission);          // 插入新权限
    Permission findById(@Param("id") Long id);  // 通过ID查找权限
    void update(Permission permission);          // 更新权限信息
    void delete(@Param("id") Long id);  // 逻辑删除权限
    List<Permission> findAll();            // 查找所有未删除权限
}