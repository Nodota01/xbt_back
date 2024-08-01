package com.xbt.controller;

import com.xbt.model.Response;
import com.xbt.util.RedisUtil;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.Base64;

@RestController
public class CaptchaController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping(value = "/api/captcha", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getCaptcha(@RequestParam String username) {
        try {
            // 生成验证码
            Pair<String, String> captcha = generateCaptcha();
            // 将验证码存入Redis，设置有效期为5分钟
            redisUtil.setWithExpire("captcha:" + username.trim(), captcha.getValue(), 5, TimeUnit.MINUTES);

            // 生成验证码图片
            String base64Image = generateCaptchaImage(captcha.getKey());
            return Response.success("验证码生成成功", base64Image);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("验证码生成失败");
        }
    }

//    private String generateCaptcha() {
//        int length = 4;
//        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789012345678901234567890123456789";
//        StringBuilder captcha = new StringBuilder();
//        Random random = new Random();
//        for (int i = 0; i < length; i++) {
//            captcha.append(chars.charAt(random.nextInt(chars.length())));
//        }
//        return captcha.toString();
//    }

    private Pair<String, String> generateCaptcha(){
        /**
         * 生成验证码，一个问题字符串，一个答案，答案存入redis
         */
        Random random = new Random();
        int a = Math.abs(random.nextInt()) % 30;
        int b = Math.abs(random.nextInt()) % 10;
        int operation = Math.abs(random.nextInt()) % 3;
        String operationChars = "+-*";
        int ans = 0;
        switch (operation){
            case 0:
                ans = a + b;
                break;
            case 1:
                ans = a - b;
                break;
            case 2:
                ans = a * b;
                break;
        }
        return new Pair<>(String.format("%d%c%d", a, operationChars.charAt(operation), b), String.valueOf(ans));
    }

    private String generateCaptchaImage(String captcha) throws Exception {
        int width = 120;
        int height = 60;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        Random random = new Random();

        // 背景颜色
        g.setColor(getRandomColor(200, 250));
        g.fillRect(0, 0, width, height);

        // 字体颜色和风格
        g.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 30));
        g.setColor(getRandomColor(160, 200));

        // 减少线条数量，优化线条颜色
        for (int i = 0; i < 5; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            int thickness = random.nextInt(3) + 1;
            g.setStroke(new BasicStroke(thickness));
            g.setColor(getRandomColor(0, 150)); // 更暗的颜色，避免与字符颜色冲突
            g.drawLine(x1, y1, x2, y2);
        }

        // 绘制验证码文本，优化位置和旋转
        int xStart = 5;
        int yStart = 10; // 初始y位置
        int spacing = 25; // 字符间距
        for (int i = 0; i < captcha.length(); i++) {
            char c = captcha.charAt(i);
            int fontSize = 30 + random.nextInt(10); // 字体大小随机
            Font font = new Font("Arial", Font.BOLD + Font.ITALIC, fontSize);
            g.setFont(font);
            AffineTransform orig = g.getTransform();
            int x = xStart + i * spacing;
            int y = yStart; // 上下微调
            g.translate(x, y);
            g.rotate(Math.toRadians(random.nextInt(60) - 30)); // 控制旋转角度在-5到5度之间
            g.setColor(getRandomColor(0, 150));
            g.drawString(String.valueOf(c), 0, fontSize);
            g.setTransform(orig);
        }

        g.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    private Color getRandomColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}