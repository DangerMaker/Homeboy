package com.adg.homeboy.ui.webview;

import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseFragment;

/**
 * Created by liuxiaoyu on 2018/3/18.
 */

public class WebViewFragment extends BaseFragment {

    private ProgressBar pbProgress;
    WebView webView;
    String url;

    public static WebViewFragment getInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        fragment.url = url;
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void onCreateView() {
        pbProgress = (ProgressBar)rootView.findViewById(R.id.progressBar);
        webView = (WebView) rootView.findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebViewClient(new WebViewClient());
        pbProgress.setMax(100);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress == 100) {
                    // 网页加载完成
                    pbProgress.setVisibility(View.GONE);
                } else {
                    // 加载中
                    pbProgress.setProgress(newProgress);
                }
            }
        });

        webView.loadUrl(url);
    }
}
