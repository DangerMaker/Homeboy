package com.adg.homeboy.ui.movie;

import android.content.Context;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseActivity;
import com.adg.homeboy.repository.model.MovieDetail;
import com.adg.homeboy.repository.model.MovieModel;
import com.adg.homeboy.repository.net.RetrofitHelper;
import com.adg.homeboy.repository.response.MoviePlayResp;
import com.adg.homeboy.view.WebViewJavaScriptFunction;
import com.adg.homeboy.view.X5WebView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.pili.pldroid.player.PLMediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liuxiaoyu on 2017/12/24.
 */

public class WebViewPlayActivity extends BaseActivity {

    private static final String TAG = WebViewPlayActivity.class.getSimpleName();
    X5WebView webView;
    GridLayoutManager layoutManager;
    EasyRecyclerView mRecyclerView;
    MovieDetailAdapter adapter;

    int id;

    String mVideoPath = "https://cdn.letv-cdn.com/20180320/6ClJpTRx/index.m3u8";
//    String mVideoPath = "rtmp://live.hkstv.hk.lxdns.com/live/hks";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        windowsParams();
        setContentView(R.layout.activity_movie_play);

        id = getIntent().getIntExtra("id", -1);

        webView = (X5WebView) findViewById(R.id.surface);
        mRecyclerView = (EasyRecyclerView) findViewById(R.id.recycler);
        layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getItem(position) instanceof MovieModel) {
                    return 1;
                } else if (adapter.getItem(position) instanceof MovieDetail) {
                    return 3;
                }
                return 0;
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        FrameLayout frameLayout = new FrameLayout(this);
        ProgressBar progressBar = new ProgressBar(this);
        FrameLayout.LayoutParams vlp = new FrameLayout.LayoutParams(200, 200);
        vlp.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(vlp);
        frameLayout.addView(progressBar);

        mRecyclerView.setProgressView(frameLayout);
        adapter = new MovieDetailAdapter(this);
        mRecyclerView.setAdapterWithProgress(adapter);

        
        setView();
        getData();
    }

    private void setView() {
        webView.loadUrl("file:///android_asset/webpage/fullscreenVideo.html");

        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        webView.getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        webView.addJavascriptInterface(new WebViewJavaScriptFunction() {

            @Override
            public void onJsFunctionCalled(String tag) {
                // TODO Auto-generated method stub

            }

            @JavascriptInterface
            public void onX5ButtonClicked() {
                WebViewPlayActivity.this.enableX5FullscreenFunc();
            }

            @JavascriptInterface
            public void onCustomButtonClicked() {
                WebViewPlayActivity.this.disableX5FullscreenFunc();
            }

            @JavascriptInterface
            public void onLiteWndButtonClicked() {
                WebViewPlayActivity.this.enableLiteWndFunc();
            }

            @JavascriptInterface
            public void onPageVideoClicked() {
                WebViewPlayActivity.this.enablePageVideoFunc();
            }
        }, "Android");
    }

    private void getData() {
        Call<MoviePlayResp> detail = RetrofitHelper.getMoiveApi().getSingle(id);
        detail.enqueue(new Callback<MoviePlayResp>() {
            @Override
            public void onResponse(Call<MoviePlayResp> call, Response<MoviePlayResp> response) {
                if (response.isSuccessful()) {
                    List<Object> obs = new ArrayList<Object>();
                    obs.add(response.body().data.get(0));
                    adapter.addAll(obs);
                    mVideoPath = response.body().data.get(0).playurl;

                }
            }

            @Override
            public void onFailure(Call<MoviePlayResp> call, Throwable t) {

            }
        });

//        MoviePlayResp.MoviePlayModel resp = AmazingStore.getMoviePlayResp().data;
//        List<Object> list = new ArrayList<>();
//        MovieDetail detail = new MovieDetail();
//        detail.name = resp.name;
//        detail.des = "简介：" + resp.des;
//        detail.length = resp.length;
//        detail.score = resp.score;
//        list.add(detail);
//        for (int i = 0; i < resp.list.size(); i++) {
//            list.add(resp.list.get(i));
//        }

//        adapter.addAll(list);

    }

    private void enableX5FullscreenFunc() {

        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "开启X5全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void disableX5FullscreenFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "恢复webkit初始状态", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", true);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void enableLiteWndFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "开启小窗模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", true);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void enablePageVideoFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "页面内全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 1);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void windowsParams() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
