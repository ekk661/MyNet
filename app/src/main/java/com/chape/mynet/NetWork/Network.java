package com.chape.mynet.NetWork;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/1/1.
 */

public class Network {
    private static OkHttpClient httpClient;
    private static Retrofit retrofit;

    public static Retrofit initRetrofit(){
        //缓存路径和大小
        File httpCacheDirectory =new File(Environment.getExternalStorageDirectory(),"HttpCache");
        int cacheSize=10*1024*1024;
        Cache cache=new Cache(httpCacheDirectory,cacheSize);
        //日志拦截器
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时
                .readTimeout(10,TimeUnit.SECONDS)//读取超时
                .writeTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)//添加缓存拦截器
                .cache(cache)//把缓存添加进来
                .build();
        retrofit=new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR=new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request=chain.request();
            Response response=chain.proceed(request);

            return response;
        }
    };


}
