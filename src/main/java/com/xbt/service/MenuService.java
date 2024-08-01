package com.xbt.service;

import com.xbt.mapper.MenuMapper;
import com.xbt.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单服务类
 */
@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 添加新菜单
     *
     * @param menu      菜单信息
     * @param createdBy 创建人
     */
    public void addMenu(Menu menu, String createdBy) {
        menu.setCreatedBy(createdBy);
        menu.setUpdatedBy(createdBy);
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        menuMapper.insert(menu);
    }

    /**
     * 查找所有菜单
     *
     * @return 菜单列表
     */
    public List<Menu> findAll() {
        return menuMapper.findAll();
    }

    /**
     * 删除菜单
     *
     * @param id        菜单ID
     * @param updatedBy 修改人
     */
    public void delete(Long id, String updatedBy) {
        menuMapper.delete(id);
    }

    /**
     * 更新菜单信息
     * @param menu 菜单信息
     * @param updatedBy 修改人
     */
    public void update(Menu menu, String updatedBy) {
        menu.setUpdatedBy(updatedBy);
        menu.setUpdateTime(LocalDateTime.now());
        menuMapper.update(menu);
    }

// 其他菜单管理相关服务方法
}