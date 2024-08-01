package com.xbt.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 响应实体类
 */
@Data
public class Response<T> implements Serializable {
    // 定义响应状态码常量
    public static final int SUCCESS_CODE = 200;
    public static final int ERROR_CODE = 500;

    public static final int PARAMS_ERROR_CODE = 400001;

    private int code;
    private String msg;
    private T data;

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // getters and setters

    public static <T> Response<T> success(String msg, T data) {
        return new Response<>(SUCCESS_CODE, msg, data);
    }

    public static Response<?> error(String msg) {
        return new Response<>(ERROR_CODE, msg, null);
    }

    public static Response<?> build(int code,String msg) {
        return new Response<>(code, msg, null);
    }
}
