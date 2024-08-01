package com.xbt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 应用启动时预热连接池
 */
@Component
public class ApplicationStartup implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // 执行一个简单的查询以预热连接池
        jdbcTemplate.execute("SELECT 1");
    }
}