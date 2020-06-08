package com.app.ccmvp.mvp.contract;

import com.app.ccmvp.base.BaseModel;
import com.app.ccmvp.base.BasePresenter;
import com.app.ccmvp.base.BaseView;
import com.app.ccmvp.bean.SystemData;

import io.reactivex.Observable;

/**
 * Created by cc on 2018/10/30.
 */

public interface SystemContract {
    interface Model extends BaseModel {
        //实现接口必须实现的方法
        Observable<SystemData> getSystemData();
    }

    abstract class Presenter extends BasePresenter<BaseView<SystemData>, Model> {
        public abstract void getSystemData();
    }
}
