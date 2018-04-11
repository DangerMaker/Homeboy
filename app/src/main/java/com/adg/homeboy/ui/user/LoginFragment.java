package com.adg.homeboy.ui.user;

import android.app.Application;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.adg.homeboy.App;
import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseFragment;
import com.adg.homeboy.repository.model.UserModel;
import com.adg.homeboy.repository.net.RetrofitHelper;
import com.adg.homeboy.repository.response.LoginResp;
import com.adg.homeboy.repository.user.UserJsonStore;
import com.adg.homeboy.util.SystemUtils;
import com.adg.homeboy.view.MyVideoView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liuxiaoyu on 2018/3/1.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    RelativeLayout idLogin;
    RelativeLayout backLayout;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_login;
    }

    @Override
    protected void onCreateView() {
        idLogin = (RelativeLayout) rootView.findViewById(R.id.id_in);
        idLogin.setOnClickListener(this);


        backLayout = (RelativeLayout) rootView.findViewById(R.id.back_layout);
        backLayout.setOnClickListener(this);
    }

    private void login(String arg1, String arg2, String arg3) {
        Call<LoginResp> resp = RetrofitHelper.getAuthApi().login(arg1, arg2, arg3);
        resp.enqueue(new Callback<LoginResp>() {
            @Override
            public void onResponse(Call<LoginResp> call, Response<LoginResp> response) {
                System.out.println("success");

                if(response != null && response.isSuccessful()) {
                    if(response.body() != null && response.body().code.equals("1")) {
                        UserModel model = response.body().data.get(0);
                        UserJsonStore.getInstance().saveUser(model.toJson());
                        getActivity().finish();
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResp> call, Throwable t) {
                System.out.println("failure");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_in:
                login("macaddr", SystemUtils.getMacAddr((Application) App.getInstance()), null);
                break;

            case R.id.back_layout:
                getActivity().finish();
                break;
        }
    }
}
