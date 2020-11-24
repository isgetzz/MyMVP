package com.app.ccmvp.mvp.presenter;

import com.app.ccmvp.base.BaseView;
import com.app.ccmvp.bean.NewResultData;
import com.app.ccmvp.net.BaseObserver;
import com.app.ccmvp.mvp.contract.SystemContract;
import com.app.ccmvp.mvp.model.SystemModel;

import io.reactivex.disposables.Disposable;

/**
 * Created by cc on 2018/10/30.
 * 用于单个接口
 */

public class SystemPresenter extends SystemContract.Presenter {

    public SystemPresenter(BaseView view) {
        mModel = new SystemModel();
        mView = view;
    }

    @Override
    public void getSystemData() {
        Disposable disposable = mModel.getSystemData().subscribeWith(new BaseObserver<NewResultData>() {
            @Override
            protected void onSuccess(NewResultData o) {
                mView.onSucceed(o);
            }

            @Override
            protected void onFailure(String errorMsg) {
                mView.onFail(errorMsg);
            }
        });
        addDisposable(disposable);
    }
}
