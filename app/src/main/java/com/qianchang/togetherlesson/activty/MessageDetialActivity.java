package com.qianchang.togetherlesson.activty;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.http.api.UrlApi;

/**
 * Created by Administrator on 2017/5/21.
 */

public class MessageDetialActivity extends BaseSwipeBackCompatActivity {

    private WebView webView;// web页面
    private WebSettings webSettings;// web页面设置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        initView();
        initInfo();
    }

    private void initView() {
        setTitle(getIntent().getStringExtra("title"));
        webView = (WebView) findViewById(R.id.webview);

    }

    private void initInfo() {
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.loadUrl(UrlApi.ip +"Message/messageshow?"+"id="+getIntent().getStringExtra("id"));
        Log.d("HomeBannerDetial",UrlApi.ip +"Message/messageshow?"+"id="+getIntent().getStringExtra("id"));


    }

}
