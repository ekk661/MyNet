package com.chape.mynet.NetWork;

import android.os.Environment;

import com.chape.mynet.NetWork.User.UserApi;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/1/1.
 */

public class NetHelper {
    private static OkHttpClient httpClient;
    private static UserApi userApi;
    private static HttpLoggingInterceptor interceptor;
    public static UserApi getUserAPI(){
        //缓存路径和大小
        File httpCacheDirectory =new File(Environment.getExternalStorageDirectory(),"HttpCache");
        int cacheSize=10*1024*1024;
        Cache cache=new Cache(httpCacheDirectory,cacheSize);
        //日志拦截器
        interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时
                .readTimeout(10,TimeUnit.SECONDS)//读取超时
                .writeTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
            //    .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)//添加缓存拦截器
                .cache(cache)//把缓存添加进来
                .build();
        if(userApi==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.zhuangbi.info/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build();
            userApi=retrofit.create(UserApi.class);
            }
            return userApi;
        }




}
