package com.app.ccmvp.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by cc on 2018/10/30.
 */

public class BasePresenter<V extends BaseView, M extends BaseModel> {

    protected V mView;
    protected M mModel;

    private CompositeDisposable compositeDisposable;

    protected void addDisposable(Disposable disposable) {
        //csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void unSubscribe() {
        if (mView != null) {
            mView = null;
        }
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

}
