package com.iyoyogo.android.ui.common;


import android.content.Intent;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;

public class WebActivity extends BaseActivity {

    private WebView mWebView;
    private TextView tvTitle;

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();

        String url = intent.getStringExtra("url");

        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings ws = mWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDefaultTextEncodingName("UTF-8");
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        ws.setJavaScriptCanOpenWindowsAutomatically(false);//设置js可以直接打开窗口，如window.open()，默认为false
        ws.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        ws.setAllowFileAccess(true);//设置可访问文件
        ws.setSupportZoom(true);//是否可以缩放，默认true
        ws.setBuiltInZoomControls(false);//是否显示缩放按钮，默认false
        ws.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        ws.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        ws.setAppCacheEnabled(true);//是否使用缓存
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        ws.setDomStorageEnabled(true);//DOM Storage
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
        });

        tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText("悠悠行旅");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
        }

    }
}
