package com.app.ccmvp.mvp.model;

import com.app.ccmvp.api.ApiEngine;
import com.app.ccmvp.mvp.contract.MainContract;
import com.app.ccmvp.rx.RxSchedulers;

import io.reactivex.Observable;

/**
 * Created by cc on 2018/10/30.
 */

public class MainModel implements MainContract.Model {
    @Override
    public Observable getMainData() {
        return ApiEngine.getInstance().getApiService()
                .getMainData().compose(RxSchedulers.switchThread());
    }

    @Override
    public Observable getSystemData() {
        return ApiEngine.getInstance().getApiService()
                .getSystemData().compose(RxSchedulers.switchThread());
    }

}
