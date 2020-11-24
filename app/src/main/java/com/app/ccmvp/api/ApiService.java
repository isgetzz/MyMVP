package com.app.ccmvp.api;

import com.app.ccmvp.bean.FileBean;
import com.app.ccmvp.bean.NewResultData;
import com.app.ccmvp.bean.NewResultDataB;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 定义api接口
 * Created by cc on 2018/10/30.
 */
public interface ApiService {
    String BASE_URL = "http://v.juhe.cn/";//聚合api免费api接口测试

    @GET("toutiao/index")
    Observable<NewResultData> getNewDataTop(@Query("type") String type, @Query("key") String key);

    @GET("toutiao/index")
    Observable<NewResultDataB> getNewDataSheHui(@Query("type") String type, @Query("key") String key);


    /*断点续传下载接口,大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @Streaming
    @GET
    Observable<Response> downFile(@Header("range") String start, @Url String url);

    @Multipart
    @Headers({"Domain-Name: douban"})
    @POST("ZL_Ticket_Item_Video/uploadAudio")
    Observable<FileBean> uploadSingleImg(@Part("description") RequestBody description, @Part MultipartBody.Part file);

    // Call<HttpBaseResult<List<PicResultData>>> uploadMultiImgs(@PartMap Map<String, RequestBody> maps);
    @Multipart
    @Headers({"Domain-Name: douban"})
    @POST("ZL_Ticket_Item_Video/uploadAudio")
    Observable<FileBean> uploadSingleImg(@Part List<MultipartBody.Part> files);

    @Multipart
    @POST("ZL_Ticket_Item_Video/uploadAudio")
    Observable<FileBean> uploadInfo(@Header("jwt-token") String token, @PartMap Map<String, RequestBody> files);

}


