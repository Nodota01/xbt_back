package com.xbt.mapper;


import com.xbt.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper {
    /**
     * 查找所有用户
     * @return 用户列表
     */
    List<User> findAll();

    /**
     * 通过ID查找用户
     * @param id 用户ID
     * @return 用户
     */
    User findById(Long id);

    /**
     * 插入用户行
     * @param user 用户实体
     */
    void insert(User user);

    /**
     * 更新用户
     * @param user 用户实体
     */
    void update(User user);

    /**
     * 删除用户
     * @param id 用户ID
     */
    void delete(Long id);

    User findByUsername(String username);

    List<User> findByConditions(@Param("username") String username, @Param("email") String email, @Param("phoneNumber") String phoneNumber); // 按条件查找用户
}