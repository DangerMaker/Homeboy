// (c)2016 Flipboard Inc, All Rights Reserved.

package com.adg.homeboy.repository.net;


import com.adg.homeboy.App;
import com.adg.homeboy.repository.response.LoginResp;
import com.adg.homeboy.repository.response.MovieListResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Description: NewsApis
 * Creator: yxc
 * date: 2016/9/8 14:05
 */

public interface AuthApi {
    String HOST = App.IP + "codeIgniter/";

    @GET("register")
    Call<LoginResp> register(@Query("username") String username, @Query("password") String wd);

    @GET("login/{auth_type}/{identifier}/{credential}")
    Call<LoginResp> login(@Path("auth_type") String auth_type, @Path("identifier") String id
            , @Path("credential") String credential);
}