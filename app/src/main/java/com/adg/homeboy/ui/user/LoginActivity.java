package com.adg.homeboy.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseActivity;
import com.github.zackratos.ultimatebar.UltimateBar;

/**
 * Created by liuxiaoyu on 2018/3/6.
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UltimateBar.newHideBuilder()
                .applyNav(true)         // 是否应用到导航栏
                .build(this)
                .apply();
    }
}
