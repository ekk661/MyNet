package com.chape.mynet.NetWork.Gan;

import com.chape.mynet.NetWork.Gan.Gan;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2018/1/1.
 */

public interface Ganki {
    @GET("data/福利/{number}/{page}")
    Call<Gan> getGan(@Path("number") int number, @Path("page") int page);
}
