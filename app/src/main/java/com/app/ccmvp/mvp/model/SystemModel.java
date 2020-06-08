package com.app.ccmvp.mvp.model;

import com.app.ccmvp.api.ApiEngine;
import com.app.ccmvp.mvp.contract.SystemContract;
import com.app.ccmvp.rx.RxSchedulers;

import io.reactivex.Observable;

/**
 * Created by cc on 2018/10/30.
 */
public class SystemModel implements SystemContract.Model {
    @Override
    public Observable getSystemData() {
        return ApiEngine.getInstance().getApiService()
                .getSystemData().compose(RxSchedulers.switchThread());
    }
}
