package com.app.ccmvp.util;

import android.content.Context;
import android.util.Log;

import com.app.ccmvp.api.ApiEngine;
import com.app.ccmvp.bean.FileBean;
import com.app.ccmvp.net.BaseObserver;
import com.app.ccmvp.rx.RxSchedulers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.baselib.util.FileUtil.getFilePath;

/**
 * Rxjava2+Retrofit2 上传文件
 */
public class uploadService {
    public void upload(Context context) {
        File file = getFilePath(context, "Test", "test.txt");
        File file1 = getFilePath(context, "Test", "test1.txt");
        //单个文件上传
        //请求体
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //描述
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), "文件下载");
        //单个文件请求
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        //更改baseUrl
        RetrofitUrlManager.getInstance().putDomain("douban", " http://60.190.73.158:58203/api/");
        Observable<FileBean> observable = ApiEngine.getInstance().getApiService().uploadSingleImg(description, part).compose(RxSchedulers.switchThread());
        //单个文件上传
        observable.subscribeWith(new BaseObserver<FileBean>() {
            @Override
            protected void onSuccess(FileBean FileBean) {
                Log.e("onSuccess", FileBean.getInfomation() + "==" + FileBean.getFileName() + "==" + FileBean.getMsg());

            }

            @Override
            protected void onFailure(String errorMsg) {

            }
        });
        //多个文件上传
        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), "DADAD");
        RequestBody imageBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
        RetrofitUrlManager.getInstance().putDomain("douban", " http://60.190.73.158:58203/api/");
        builder.addFormDataPart("11", file.getName(), imageBody);
        builder.addFormDataPart("22", file.getName(), imageBody1);
        Observable<FileBean> observable1 = ApiEngine.getInstance().getApiService().uploadSingleImg(builder.build().parts()).compose(RxSchedulers.switchThread());
        observable1.subscribeWith(new BaseObserver<FileBean>() {
            @Override
            protected void onSuccess(FileBean FileBean) {
                Log.e("onSuccess", FileBean + "=" + FileBean.getInfomation() + "==" + FileBean.getFileName() + "==" + FileBean.getMsg());

            }

            @Override
            protected void onFailure(String errorMsg) {
                Log.e("onSuccess1", errorMsg);

            }
        });
        Map<String, RequestBody> map = new HashMap<>();
        // map.put(String.format("file\"; filename=\"" + file.getName()), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        //map.put(String.format("imageFile\"; filename=\"123456.jpg"), RequestBody.create(MediaType.parse("image/jpg"), file));//图片名称不能包含中文字符
        map.put(String.format("file\"; filename=\"" + file.getName()), RequestBody.create(MediaType.parse("image/jpg"), file));
        map.put("plateNo", toRequestBody("plateNo"));
        map.put("plateColor", toRequestBody("plateColor"));
        map.put("deviceId", toRequestBody("deviceId"));

        map.put("longitude", toRequestBody("longitude"));
        map.put("latitude", toRequestBody("address"));
        map.put("address", toRequestBody("_非空值"));
        map.put("gatherTime", toRequestBody("yyyy-MM-dd HH:mm:ss"));
        Observable<FileBean> observable2 = ApiEngine.getInstance().getApiService().uploadInfo("DADAKD", map).compose(RxSchedulers.switchThread());
        //多个文件上传
        observable2.subscribeWith(new BaseObserver<FileBean>() {
            @Override
            protected void onSuccess(FileBean FileBean) {

            }

            @Override
            protected void onFailure(String errorMsg) {

            }
        });
    }

    private RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }

}
