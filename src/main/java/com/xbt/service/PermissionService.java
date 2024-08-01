package com.xbt.service;

import com.xbt.mapper.PermissionMapper;
import com.xbt.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限服务类
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 添加新权限
     * @param permission 权限信息
     * @param createdBy 创建人
     */
    public void addPermission(Permission permission, String createdBy) {
        permission.setCreatedBy(createdBy);
        permission.setUpdatedBy(createdBy);
        permission.setCreateTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());
        permissionMapper.insert(permission);
    }

    /**
     * 查找所有权限
     * @return 权限列表
     */
    public List<Permission> findAll() {
        return permissionMapper.findAll();
    }

    /**
     * 删除权限
     * @param id 权限ID
     * @param updatedBy 修改人
     */
    public void delete(Long id, String updatedBy) {
        permissionMapper.delete(id);
    }

    // 其他权限管理相关服务方法
}