package com.xbt.controller;

import com.xbt.model.Permission;
import com.xbt.model.Response;
import com.xbt.service.PermissionService;
import com.xbt.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限控制器类
 */
@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 添加新权限
     * @param permission 权限信息
     * @param token JWT token
     * @return 添加后的权限信息
     */
    @PostMapping
    public ResponseEntity<Response<Permission>> addPermission(@RequestBody Permission permission, @RequestHeader("Authorization") String token) {
        String createdBy = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        permissionService.addPermission(permission, createdBy);
        return ResponseEntity.ok(Response.success("权限添加成功", permission));
    }

    /**
     * 查找所有权限
     * @return 权限列表
     */
    @GetMapping
    public ResponseEntity<Response<List<Permission>>> getAllPermissions() {
        List<Permission> permissions = permissionService.findAll();
        return ResponseEntity.ok(Response.success("权限列表查询成功", permissions));
    }

    /**
     * 删除权限
     * @param id 权限ID
     * @param token JWT token
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deletePermission(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String updatedBy = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        permissionService.delete(id, updatedBy);
        return ResponseEntity.ok(Response.success("权限删除成功", null));
    }

    // 其他权限管理相关端点
}