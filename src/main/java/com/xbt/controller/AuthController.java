package com.xbt.controller;

import com.xbt.model.Menu;
import com.xbt.model.Response;
import com.xbt.model.User;
import com.xbt.service.LoginAttemptService;
import com.xbt.service.UserDetailsServiceImpl;
import com.xbt.util.JwtTokenUtil;
import com.xbt.util.RedisUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 认证控制器
 */
@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PasswordEncoder passwordEncoder; // 添加密码加密器

    /**
     * 登录接口
     * @param session HttpSession
     * @return 登录结果
     */
    @PostMapping("/api/login")
    public Response<Map<String, Object>> login(@Valid @RequestBody User user, HttpSession session) {
        String username = user.getUsername();

        // 检查账户是否被锁定
        if (loginAttemptService.isBlocked(username)) {
            return new Response<>(403, "账户已被锁定，请稍后再试", null);
        }

        // 校验验证码（假设前端传递的验证码是user.getCaptcha()）
        String captcha = redisUtil.get("captcha:" + username.trim());
        if (captcha == null || !captcha.equalsIgnoreCase(user.getCaptcha())) {
            loginAttemptService.loginFailed(username);
            return new Response<>(400, "验证码错误", null);
        }

        try {
            // 验证用户密码
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails == null || !passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
                loginAttemptService.loginFailed(username);
                return new Response<>(500, "用户名或密码错误", null);
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 生成JWT Token
            String token = jwtTokenUtil.generateToken(userDetails);

            // 获取用户菜单权限信息
            User userEntity = (User) userDetails;
            Set<Menu> menus = userEntity.getMenus();

            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("menus", menus);

            loginAttemptService.loginSucceeded(username);
            return new Response<>(200, "登录成功", result);
        } catch (Exception e) {
            loginAttemptService.loginFailed(username);
            return new Response<>(500, "登录失败: " + e.getMessage(), null);
        }
    }
}