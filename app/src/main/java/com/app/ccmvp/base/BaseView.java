package com.app.ccmvp.base;


/**
 * Created by cc on 2018/10/30.
 * T 返回的数据类型跟定义的一致，如果多个接口同时调用就用共同的父类，根据数据在强转成子类
 */

public interface BaseView<T> {
    void onFail(String err);

    void onSucceed(T data);
}