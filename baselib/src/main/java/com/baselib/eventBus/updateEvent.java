package com.baselib.eventBus;

/**
 * 更新通信
 */
public class updateEvent {
    private int code = -1;
    private boolean update = false;
    private String content = "";

    public updateEvent(String content) {
        this.content = content;
    }

    public updateEvent(boolean update) {
        this.update = update;
    }

    public updateEvent(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
