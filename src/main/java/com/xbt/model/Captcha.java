package com.xbt.model;

/**
 * 验证码模型类
 */
public class Captcha {
    private String base64Image;
    private String sessionId;

    public Captcha(String base64Image, String sessionId) {
        this.base64Image = base64Image;
        this.sessionId = sessionId;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}