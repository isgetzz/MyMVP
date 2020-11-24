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
                .getNewDataSheHui("", "5f902ef52c18d3b3e70a4c3c6185bef9").compose(RxSchedulers.switchThread());
    }
}
