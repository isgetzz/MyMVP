package com.app.ccmvp.api;

import com.app.ccmvp.bean.MainData;
import com.app.ccmvp.bean.SystemData;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 定义api接口
 * Created by cc on 2018/10/30.
 */
public interface ApiService {
    String BASE_URL =/*"http://cs.gxshop.api.tutujf.com/" */ "http://shopapi.cngaoluo.com/";
    @GET("Api/App/GetSystemConfig")
    Observable<SystemData> getSystemData();

    @GET("Api/App/GetIndex?version=10")
    Observable<MainData> getMainData();
    /*断点续传下载接口,大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @Streaming
    @GET
    Observable<Response> downFile(@Header("range") String start, @Url String url);
}
