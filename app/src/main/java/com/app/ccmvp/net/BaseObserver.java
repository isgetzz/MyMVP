package com.app.ccmvp.net;

import io.reactivex.observers.ResourceObserver;

public abstract class BaseObserver<T> extends ResourceObserver<T> {

    @Override
    public void onNext(T o) {
        onSuccess(o);
    }

    @Override
    public void onError(Throwable e) {
        onFailure(ExceptionHandle.handleException(e).message);
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(T t);

    protected abstract void onFailure(String errorMsg);
}
