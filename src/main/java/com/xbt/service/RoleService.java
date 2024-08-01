package com.xbt.service;

import com.xbt.mapper.RoleMapper;
import com.xbt.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色服务类
 */
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 添加新角色
     * @param role 角色信息
     * @param createdBy 创建人
     */
    public void addRole(Role role, String createdBy) {
        role.setCreatedBy(createdBy);
        role.setUpdatedBy(createdBy);
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.insert(role);
    }

    /**
     * 查找所有角色
     * @return 角色列表
     */
    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    /**
     * 删除角色
     * @param id 角色ID
     * @param updatedBy 修改人
     */
    public void delete(Long id, String updatedBy) {
        roleMapper.delete(id);
    }

    // 其他角色管理相关服务方法
}