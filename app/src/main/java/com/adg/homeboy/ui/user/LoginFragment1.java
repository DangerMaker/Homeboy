package com.adg.homeboy.ui.user;

import android.view.View;
import android.widget.Button;

import com.adg.homeboy.App;
import com.adg.homeboy.Config;
import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseFragment;
import com.adg.homeboy.repository.net.RetrofitHelper;
import com.adg.homeboy.repository.response.LoginResp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liuxiaoyu on 2018/3/1.
 */

public class LoginFragment1 extends BaseFragment {
    Button button;
    Button button2;
    Button button3;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_login1;
    }

    @Override
    protected void onCreateView() {
        button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

//        button2 = (Button) rootView.findViewById(R.id.button2);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getVipMovie();
//            }
//        });
//
//        button3 = (Button) rootView.findViewById(R.id.button3);
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                register();
//            }
//        });
    }

    private void login() {
//        Call<LoginResp> resp = RetrofitHelper.getAuthApi().login("主人","123456");
//        resp.enqueue(new Callback<LoginResp>() {
//            @Override
//            public void onResponse(Call<LoginResp> call, Response<LoginResp> response) {
//                App.COOKIE = response.headers().get("Set-Cookie");
//                System.out.println("success");
//            }
//
//            @Override
//            public void onFailure(Call<LoginResp> call, Throwable t) {
//                System.out.println("failure");
//            }
//        });
    }

//    private void getVipMovie() {
//        Call<LoginResp> resp = RetrofitHelper.getMoiveApi().getVipMovie(App.COOKIE);
//        resp.enqueue(new Callback<LoginResp>() {
//            @Override
//            public void onResponse(Call<LoginResp> call, Response<LoginResp> response) {
//                System.out.println("success");
//            }
//
//            @Override
//            public void onFailure(Call<LoginResp> call, Throwable t) {
//                System.out.println("failure");
//            }
//        });
//    }
//
//    private void register() {
//        Call<LoginResp> resp = RetrofitHelper.getAuthApi().register("十一","123456");
//        resp.enqueue(new Callback<LoginResp>() {
//            @Override
//            public void onResponse(Call<LoginResp> call, Response<LoginResp> response) {
//                System.out.println("success");
//            }
//
//            @Override
//            public void onFailure(Call<LoginResp> call, Throwable t) {
//                System.out.println("failure");
//            }
//        });
//    }

}
