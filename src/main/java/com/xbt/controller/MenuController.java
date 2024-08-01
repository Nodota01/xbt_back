package com.xbt.controller;

import com.xbt.model.Menu;
import com.xbt.model.Response;
import com.xbt.service.MenuService;
import com.xbt.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单控制器类
 */
@RestController
@RequestMapping("/api/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 添加新菜单
     * @param menu 菜单信息
     * @param token JWT token
     * @return 添加后的菜单信息
     */
    @PostMapping
    public ResponseEntity<Response<Menu>> addMenu(@RequestBody Menu menu, @RequestHeader("Authorization") String token) {
        String createdBy = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        menuService.addMenu(menu, createdBy);
        return ResponseEntity.ok(Response.success("菜单添加成功", menu));
    }

    /**
     * 查找所有菜单
     * @return 菜单列表
     */
    @GetMapping
    public ResponseEntity<Response<List<Menu>>> getAllMenus() {
        List<Menu> menus = menuService.findAll();
        return ResponseEntity.ok(Response.success("菜单列表查询成功", menus));
    }

    /**
     * 删除菜单
     * @param id 菜单ID
     * @param token JWT token
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteMenu(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String updatedBy = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        menuService.delete(id, updatedBy);
        return ResponseEntity.ok(Response.success("菜单删除成功", null));
    }

    /**
     * 更新菜单信息
     * @param menu 菜单信息
     * @param token JWT token
     * @return 更新后的菜单信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<Response<Menu>> updateMenu(@RequestBody Menu menu, @RequestHeader("Authorization") String token) {
        String updatedBy = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        menuService.update(menu, updatedBy);
        return ResponseEntity.ok(Response.success("菜单更新成功", menu));
    }

    // 其他菜单管理相关端点
}