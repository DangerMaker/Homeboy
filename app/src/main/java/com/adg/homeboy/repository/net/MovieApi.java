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
//    String HOST = "http://192.168.2.108:8888/";
    String HOST = App.IP + "tp5/index.php/";

    @GET("movie/{id}")
    Call<MoviePlayResp> getSingle(@Path("id") int id);

//    @Headers({"Content-Type: application/json","Accept: application/json"})
    @GET("homepage")
    Call<AmazingModelResp> getHomePage();

    @GET("typelist")
    Call<MoiveTypeMapResp> getTypeMap();

    @GET("query")
    Call<MovieListResp> getList(@Query("typeid") String typeid,@Query("wd") String wd,
                                @Query("year") String year,@Query("page") int page);

    @GET("hotword")
    Call<MoiveTypeMapResp> getHotword();

    @GET("vipmovie")
    Call<LoginResp> getVipMovie(@Header("Cookie") String cookie);

}