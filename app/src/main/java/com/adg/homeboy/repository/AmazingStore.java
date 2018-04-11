package com.adg.homeboy.repository;

import com.adg.homeboy.repository.net.MovieApi;
import com.adg.homeboy.repository.net.RetrofitHelper;
import com.adg.homeboy.repository.response.AmazingModelResp;
import com.adg.homeboy.repository.response.MoiveTypeMapResp;
import com.adg.homeboy.repository.response.MoviePlayResp;
import com.adg.homeboy.util.LocalJsonReadUtil;

/**
 * Created by liuxiaoyu on 2017/12/21.
 */

public class AmazingStore {

//    public static AmazingStore store;
//    public static MovieApi moiveApi;
//
//    public AmazingStore(){
//        moiveApi = RetrofitHelper.getMoiveApi();
//    }
//
//    public static AmazingStore getInstance() {
//        if(store == null){
//            store = new AmazingStore();
//        }
//        return store;
//    }
//
    public static AmazingModelResp getAmazingResp(){
        AmazingModelResp resp = LocalJsonReadUtil.getModelFromFile("custom_home_type.json",AmazingModelResp.class);
        return resp;
    }

    public static MoviePlayResp getMoviePlayResp(){
        MoviePlayResp resp = LocalJsonReadUtil.getModelFromFile("custom_movie_detail.json",MoviePlayResp.class);
        return resp;
    }

    public static MoiveTypeMapResp getTypeMap(){
        MoiveTypeMapResp resp = LocalJsonReadUtil.getModelFromFile("type_map.json",MoiveTypeMapResp.class);
        return resp;
    }
}
