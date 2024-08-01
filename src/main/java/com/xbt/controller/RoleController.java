package com.xbt.controller;

import com.xbt.model.Response;
import com.xbt.model.Role;
import com.xbt.service.RoleService;
import com.xbt.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色控制器类
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 添加新角色
     * @param role 角色信息
     * @param token JWT token
     * @return 添加后的角色信息
     */
    @PostMapping
    public ResponseEntity<Response<Role>> addRole(@RequestBody Role role, @RequestHeader("Authorization") String token) {
        String createdBy = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        roleService.addRole(role, createdBy);
        return ResponseEntity.ok(Response.success("角色添加成功", role));
    }

    /**
     * 查找所有角色
     * @return 角色列表
     */
    @GetMapping
    public ResponseEntity<Response<List<Role>>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(Response.success("角色列表查询成功", roles));
    }

    /**
     * 删除角色
     * @param id 角色ID
     * @param token JWT token
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteRole(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String updatedBy = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        roleService.delete(id, updatedBy);
        return ResponseEntity.ok(Response.success("角色删除成功", null));
    }

    // 其他角色管理相关端点
}