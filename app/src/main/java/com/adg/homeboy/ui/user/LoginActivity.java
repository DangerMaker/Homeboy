package com.adg.homeboy.ui.user;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseActivity;
import com.adg.homeboy.view.MyVideoView;
import com.github.zackratos.ultimatebar.UltimateBar;

/**
 * Created by liuxiaoyu on 2018/3/6.
 */

public class LoginActivity extends BaseActivity {

    MyVideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        videoView = (MyVideoView) findViewById(R.id.videoview);

        final Uri videoPath = Uri.parse("android.resource://" + mContext.getPackageName() + "/" +R.raw.rotate_output);
        videoView.setVideoURI(videoPath);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }});

        UltimateBar.newImmersionBuilder()
                .applyNav(true)         // 是否应用到导航栏
                .build(this)
                .apply();
    }

}
