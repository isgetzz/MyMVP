package com.app.ccmvp.mvp.presenter;

import com.app.ccmvp.base.BaseResponse;
import com.app.ccmvp.base.BaseView;
import com.app.ccmvp.mvp.contract.MainContract;
import com.app.ccmvp.mvp.model.MainModel;
import com.app.ccmvp.net.BaseObserver;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by cc on 2018/10/30.
 * 用于多个接口
 */

public class MainPresenter extends MainContract.Presenter {
    public static final int MainData = 1;
    public static final int BaseData = 2;

    public MainPresenter(BaseView view) {
        mModel = new MainModel();
        mView = view;
    }

    @Override
    public void getMainData(int dateType) {
        Observable<BaseResponse> observable;
        if (dateType == MainData) {
            observable = mModel.getMainData();
        } else {
            observable = mModel.getSystemData();
        }
        Disposable disposable = observable.subscribeWith(new BaseObserver<BaseResponse>() {
            @Override
            protected void onSuccess(BaseResponse data) {
                mView.onSucceed(data);
            }
            @Override
            protected void onFailure(String errorMsg) {
                mView.onFail(errorMsg);
            }
        });
        addDisposable(disposable);
    }
}
