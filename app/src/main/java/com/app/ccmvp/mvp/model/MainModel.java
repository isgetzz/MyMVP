package com.app.ccmvp.mvp.model;

import com.app.ccmvp.api.ApiEngine;
import com.app.ccmvp.mvp.contract.MainContract;
import com.app.ccmvp.rx.RxSchedulers;

import io.reactivex.Observable;

/**
 * Created by cc on 2018/10/30.
 * 定义具体的数据类型
 */

public class MainModel implements MainContract.Model {
    @Override
    public Observable getTopData() {
        return ApiEngine.getInstance().getApiService().getNewDataTop("头条", "5f902ef52c18d3b3e70a4c3c6185bef9").compose(RxSchedulers.switchThread());
    }

    @Override
    public Observable getSheHuiData() {
        return ApiEngine.getInstance().getApiService()
                .getNewDataSheHui("shehui", "5f902ef52c18d3b3e70a4c3c6185bef9").compose(RxSchedulers.switchThread());
    }

}
