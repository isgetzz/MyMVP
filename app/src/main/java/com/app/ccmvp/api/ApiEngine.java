package com.app.ccmvp.api;

import com.app.ccmvp.App;

import java.io.File;
import java.util.concurrent.TimeUnit;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cc on 2018/10/30.
 */
public class ApiEngine {
    private volatile static ApiEngine apiEngine;
    private Retrofit retrofit;

    private ApiEngine() {
        // debug日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存
        int size = 1024 * 1024 * 100;
        File cacheFile = new File(App.getContext().getCacheDir(), "OkHttpCache");
        Cache cache = new Cache(cacheFile, size);
        //适配多Url问题 RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder())
        //@Headers({"Domain-Name: douban"}) // Add the Domain-Name header
        //根据名称修改对应的BaseUrl RetrofitUrlManager.getInstance().putDomain("douban", "https://api.douban.com");
         OkHttpClient client = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder())
                .connectTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                .addNetworkInterceptor(new NetworkInterceptor())
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * apiEngine获取单例
     *
     * @return apiEngine
     */
    public static ApiEngine getInstance() {
        if (apiEngine == null) {
            synchronized (ApiEngine.class) {
                if (apiEngine == null) {
                    apiEngine = new ApiEngine();
                }
            }
        }
        return apiEngine;
    }

    /**
     * retrofit 绑定类,可以根据绑定的类获取API地址
     *
     * @return
     */
    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }
}
