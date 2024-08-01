package com.xbt.service;

import com.xbt.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPT = 5;
    private static final long LOCK_TIME = 15; // 锁定时间，单位：分钟

    @Autowired
    private RedisUtil redisUtil;

    public void loginSucceeded(String username) {
        String key = getKey(username);
        redisUtil.delete(key);
    }

    public void loginFailed(String username) {
        String key = getKey(username);
        Integer attempts = redisUtil.get(key) != null ? Integer.parseInt(redisUtil.get(key)) : 0;
        attempts++;
        if (attempts >= MAX_ATTEMPT) {
            redisUtil.setWithExpire(key, String.valueOf(attempts), LOCK_TIME, TimeUnit.MINUTES);
        } else {
            redisUtil.setWithExpire(key, String.valueOf(attempts), 1, TimeUnit.DAYS);
        }
    }

    public boolean isBlocked(String username) {
        String key = getKey(username);
        Integer attempts = redisUtil.get(key) != null ? Integer.parseInt(redisUtil.get(key)) : 0;
        return attempts >= MAX_ATTEMPT;
    }

    private String getKey(String username) {
        return "loginAttempt:" + username.trim();
    }
}