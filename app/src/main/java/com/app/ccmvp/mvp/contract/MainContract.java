package com.app.ccmvp.mvp.contract;

import com.app.ccmvp.base.BaseModel;
import com.app.ccmvp.base.BasePresenter;
import com.app.ccmvp.base.BaseResponse;
import com.app.ccmvp.base.BaseView;

import io.reactivex.Observable;

/**
 * Created by cc on 2018/10/30.
 */
public interface MainContract {
    interface Model extends BaseModel {
        Observable<BaseResponse> getTopData();

        Observable<BaseResponse> getSheHuiData();
    }

    abstract class Presenter extends BasePresenter<BaseView<BaseResponse>, Model> {
        public abstract void getNewData(int dateType);
    }
}
