package com.adg.homeboy.repository.net;

import com.adg.homeboy.App;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by liuxiaoyu on 2018/4/12.
 */

public interface CommonApi {
    String HOST = App.IP + "codeIgniter/";

    @GET("update")
    Call<String> update();
}
