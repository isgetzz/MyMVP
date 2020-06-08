package com.app.ccmvp.base;


/**
 * Created by cc on 2018/10/30.
 */

public interface BaseView<T> {
    void onFail(String err);
    void onSucceed(T data);
}