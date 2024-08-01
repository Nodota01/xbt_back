package com.xbt.mapper;

import com.xbt.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色Mapper接口
 */
@Mapper
public interface RoleMapper {
    void insert(Role role);          // 插入新角色
    Role findById(@Param("id") Long id);  // 通过ID查找角色
    void update(Role role);          // 更新角色信息
    void delete(@Param("id") Long id);  // 逻辑删除角色
    List<Role> findAll();            // 查找所有未删除角色
}