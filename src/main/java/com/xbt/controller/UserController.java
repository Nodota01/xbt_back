package com.xbt.controller;

import com.xbt.model.Response;
import com.xbt.model.User;
import com.xbt.service.UserService;
import com.xbt.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器类
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtUtil;

    /**
     * 注册新用户
     * @param user 用户信息
     * @param token JWT token
     * @return 注册后的用户信息
     */
    @PostMapping("/register")
    public ResponseEntity<Response<User>> register(@RequestBody User user, @RequestHeader("Authorization") String token) {
        String createdBy = jwtUtil.getUsernameFromToken(token.substring(7));
        User newUser = userService.register(user, createdBy);
        return ResponseEntity.ok(Response.success("用户注册成功", newUser));
    }

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/{username}")
    public ResponseEntity<Response<User>> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(Response.success("用户查询成功", user));
    }

    /**
     * 查找所有用户
     * @return 用户列表
     */
    @GetMapping
    public ResponseEntity<Response<List<User>>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(Response.success("用户列表查询成功", users));
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @param token JWT token
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String updatedBy = jwtUtil.getUsernameFromToken(token.substring(7));
        userService.delete(id, updatedBy);
        return ResponseEntity.ok(Response.success("用户删除成功", null));
    }

    /**
     * 按条件查找用户
     * @param username 用户名
     * @param email 电子邮件
     * @param phoneNumber 电话号码
     * @return 用户列表
     */
    @GetMapping("/search")
    public ResponseEntity<Response<List<User>>> searchUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber) {
        List<User> users = userService.findByConditions(username, email, phoneNumber);
        return ResponseEntity.ok(Response.success("按条件查询用户成功", users));
    }

    // 其他用户管理相关端点
}