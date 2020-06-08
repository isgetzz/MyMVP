package com.app.ccmvp.base;

import java.io.Serializable;

/**
 * 基础数据类
 */
public class BaseResponse implements Serializable {
    private String code;
    private String msg;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
