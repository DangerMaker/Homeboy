package com.adg.homeboy.ui.user;

import android.app.Activity;
import android.app.Application;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
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
    MyVideoView videoView;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_login;
    }

    @Override
    protected void onCreateView() {
        idLogin = (RelativeLayout) rootView.findViewById(R.id.id_in);
        idLogin.setOnClickListener(this);


        videoView = (MyVideoView) rootView.findViewById(R.id.videoview);

        final Uri videoPath = Uri.parse("android.resource://" + mContext.getPackageName() + "/" +R.raw.rotate_output);
        videoView.setVideoURI(videoPath);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }});

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
                        UserModel model = response.body().data;
                        UserJsonStore.getInstance().saveUser(model.toJson());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((Activity)mContext).finish();
                            }
                        },3000);
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
                login("macaddr", SystemUtils.getMacAddr((Application) App.getInstance()), "0");
                break;

            case R.id.back_layout:
                getActivity().finish();
                break;
        }
    }
}
