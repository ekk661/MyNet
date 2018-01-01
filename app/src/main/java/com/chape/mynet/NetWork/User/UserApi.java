package com.chape.mynet.NetWork.User;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by Administrator on 2018/1/1.
 */

public interface UserApi {
    //登录
    @GET("{login}")
    Flowable<LoginBean> getLogin(@Path("login") String login, @Query("mobile") String mob, @Query("password") String pw);
    //注册
    @GET("{reg}")
    Flowable<RegisterBean> getReg(@Path("reg") String login,@Query("mobile")String mob,@Query("password")String pw);
    //个人详情
    @GET("{getUserInfo}")
    Flowable<UserBean> getUser(@Path("getUserInfo") String login,@Query("uid")String id);

}
