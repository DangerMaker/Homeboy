// (c)2016 Flipboard Inc, All Rights Reserved.

package com.adg.homeboy.repository.net;


import com.adg.homeboy.App;
import com.adg.homeboy.repository.response.AmazingModelResp;
import com.adg.homeboy.repository.response.LoginResp;
import com.adg.homeboy.repository.response.MoiveTypeMapResp;
import com.adg.homeboy.repository.response.MovieListResp;
import com.adg.homeboy.repository.response.MoviePlayResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Description: NewsApis
 * Creator: yxc
 * date: 2016/9/8 14:05
 */

public interface MovieApi {
    String HOST = App.IP + "codeIgniter/";

    @GET("movie/{id}")
    Call<MoviePlayResp> getSingle(@Path("id") int id);

    @GET("home")
    Call<AmazingModelResp> getHomePage();

    @GET("category")
    Call<MoiveTypeMapResp> getTypeMap();

    @GET("movies/{wd}/{d_type}/{year}/{page}")
    Call<MovieListResp> getList(@Path("d_type") int typeid,@Path("wd") String wd,
                                @Path("year") int year,@Path("page") int page);

    @GET("hotword")
    Call<MoiveTypeMapResp> getHotword();

}