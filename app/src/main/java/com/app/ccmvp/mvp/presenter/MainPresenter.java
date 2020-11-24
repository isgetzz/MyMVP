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
    public static final int TopData = 1;
    public static final int SheHuiData = 2;

    public MainPresenter(BaseView view) {
        mModel = new MainModel();
        mView = view;
    }

    @Override
    public void getNewData(int dateType) {
        Observable<BaseResponse> observable;
        switch (dateType) {//根据需要请求对应的数据
            case SheHuiData:
                observable = mModel.getSheHuiData();
                break;
            default:
                observable = mModel.getTopData();
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
