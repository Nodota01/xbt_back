package com.xbt.mapper;

import com.xbt.model.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<Menu> findMenusByUserId(int userId);

    void insert(Menu menu);          // 插入新菜单
    Menu findById(@Param("id") Long id);  // 通过ID查找菜单
    void update(Menu menu);          // 更新菜单信息
    void delete(@Param("id") Long id);  // 逻辑删除菜单
    List<Menu> findAll();            // 查找所有未删除菜单
}