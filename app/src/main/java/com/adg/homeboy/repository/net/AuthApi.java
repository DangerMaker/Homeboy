// (c)2016 Flipboard Inc, All Rights Reserved.

package com.adg.homeboy.repository.net;


import com.adg.homeboy.App;
import com.adg.homeboy.repository.response.LoginResp;
import com.adg.homeboy.repository.response.MovieListResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Description: NewsApis
 * Creator: yxc
 * date: 2016/9/8 14:05
 */

public interface AuthApi {
//    String HOST = "http://192.168.2.108:8888/";
    String HOST = App.IP + "tp5/index.php/";

//    @GET("login")
//    Call<LoginResp> login(@Query("username") String username, @Query("password") String wd);

    @GET("register")
    Call<LoginResp> register(@Query("username") String username, @Query("password") String wd);

    @GET("login")
    Call<LoginResp> login(@Query("auth_type") String auth_type, @Query("identifier") String id
            ,@Query("credential") String credential);
}