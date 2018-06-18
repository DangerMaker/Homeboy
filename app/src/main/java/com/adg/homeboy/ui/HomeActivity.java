package com.adg.homeboy.ui;

import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseActivity;
import com.adg.homeboy.util.CheckVersionUpdate;
import com.adg.homeboy.util.PermissionChecker;
import com.adg.homeboy.util.UltimateBar;
import com.adg.homeboy.util.eventbus.TabEvent;
import com.adg.homeboy.view.UnScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by liuxiaoyu on 2017/12/17.
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    View layout_YoukuHome;
    View layout_YoukuChannel;
    View layout_YouKuSubscribe;
    View layout_YoukuVip;
    View layout_YoukuUser;

    UnScrollViewPager viewpager;
    HomePageAdapter homeAdapter;
    private int initPos = 0;
    private Long firstTime = 0L;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState != null) {
            initPos = savedInstanceState.getInt("pos");
        }

        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        layout_YouKuSubscribe = findViewById(R.id.layout_subscribe);
        layout_YoukuHome = findViewById(R.id.layout_home);
        layout_YoukuVip = findViewById(R.id.layout_vip);
        layout_YoukuUser = findViewById(R.id.layout_user);
        layout_YoukuChannel = findViewById(R.id.layout_channel);
        viewpager = (UnScrollViewPager) findViewById(R.id.home_pager);

        layout_YouKuSubscribe.setOnClickListener(this);
        layout_YoukuHome.setOnClickListener(this);
        layout_YoukuVip.setOnClickListener(this);
        layout_YoukuUser.setOnClickListener(this);
        layout_YoukuChannel.setOnClickListener(this);

        viewpager.setScrollable(false);
        homeAdapter = new HomePageAdapter(getSupportFragmentManager());
        viewpager.setAdapter(homeAdapter);
        viewpager.setOffscreenPageLimit(homeAdapter.getCount());
        switchTab(initPos);

        isPermissionOK();
        CheckVersionUpdate.checkVersion(this,true);
    }

    public void switchTab(int paramInt) {
        this.layout_YoukuChannel.setSelected(false);
        this.layout_YoukuHome.setSelected(false);
        this.layout_YouKuSubscribe.setSelected(false);
        this.layout_YoukuUser.setSelected(false);
        this.layout_YoukuVip.setSelected(false);
        this.layout_YoukuChannel.setEnabled(true);
        this.layout_YoukuHome.setEnabled(true);
        this.layout_YouKuSubscribe.setEnabled(true);
        this.layout_YoukuUser.setEnabled(true);
        this.layout_YoukuVip.setEnabled(true);
        View localView;
        switch (paramInt) {
            default:
                return;
            case 0:
                localView = this.layout_YoukuHome;

//                UltimateBar.newImmersionBuilder()
//                        .build(this)
//                        .apply();

                break;
            case 1:
                localView = this.layout_YoukuChannel;

//                UltimateBar.newColorBuilder()
//                        .statusColor(color(R.color.white))   // 状态栏颜色
//                        .build(this)
//                        .apply();

                break;
            case 2:
                localView = this.layout_YouKuSubscribe;

//                UltimateBar.newColorBuilder()
//                        .statusColor(color(R.color.white))   // 状态栏颜色
//                        .build(this)
//                        .apply();
                break;
            case 3:
                localView = this.layout_YoukuVip;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        startActivity(new Intent(HomeActivity.this, FullScreenActivity.class));
                    }
                }, 1000);

//                UltimateBar.newColorBuilder()
//                        .statusColor(color(R.color.black))   // 状态栏颜色
//                        .build(this)
//                        .apply();
                break;
            case 4:
                localView = this.layout_YoukuUser;

//                UltimateBar.newColorBuilder()
//                        .statusColor(color(R.color.colorPrimary))   // 状态栏颜色
//                        .build(this)
//                        .apply();
                break;
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView()
//                    .setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }

        if (localView != null) {
            localView.setSelected(true);
            localView.setEnabled(true);
        }
        if (viewpager.getCurrentItem() != paramInt) {
            viewpager.setCurrentItem(paramInt, false);
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("pos", viewpager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_home:
                switchTab(0);
                break;
            case R.id.layout_channel:
                switchTab(1);
                break;
            case R.id.layout_subscribe:
                switchTab(2);
                break;
            case R.id.layout_vip:
                switchTab(3);
                break;
            case R.id.layout_user:
                switchTab(4);
                break;
        }
    }

    PermissionChecker checker = new PermissionChecker(this);

    public boolean isPermissionOK() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checker.checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        checker.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTabEvent(TabEvent event) {/* Do something */
        switchTab(event.position);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
