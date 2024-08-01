package com.xbt.service;

import com.xbt.mapper.UserMapper;
import com.xbt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户服务类
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 注册新用户
     * @param user 用户信息
     * @param createdBy 创建人
     * @return 注册后的用户信息
     */
    public User register(User user, String createdBy) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedBy(createdBy);
        user.setUpdatedBy(createdBy);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
        return user;
    }

    /**
     * 按条件查找用户
     * @param username 用户名
     * @param email 电子邮件
     * @param phoneNumber 电话号码
     * @return 用户列表
     */
    public List<User> findByConditions(String username, String email, String phoneNumber) {
        return userMapper.findByConditions(username, email, phoneNumber);
    }

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户信息
     */
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    /**
     * 查找所有用户
     * @return 用户列表
     */
    public List<User> findAll() {
        return userMapper.findAll();
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @param updatedBy 修改人
     */
    public void delete(Long id, String updatedBy) {
        userMapper.delete(id);
    }

    // 其他用户管理相关服务方法
}