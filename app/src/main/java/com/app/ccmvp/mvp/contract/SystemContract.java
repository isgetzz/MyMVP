package com.app.ccmvp.mvp.contract;

import com.app.ccmvp.base.BaseModel;
import com.app.ccmvp.base.BasePresenter;
import com.app.ccmvp.base.BaseView;
import com.app.ccmvp.bean.NewResultData;

import io.reactivex.Observable;

/**
 * Created by cc on 2018/10/30.
 */

public interface SystemContract {
    interface Model extends BaseModel {
        //实现接口必须实现的方法
        Observable<NewResultData> getSystemData();
    }

    abstract class Presenter extends BasePresenter<BaseView<NewResultData>, Model> {
        public abstract void getSystemData();
    }
}
