package com.qianchang.togetherlesson.activty;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.http.api.UrlApi;


/**
 * Created by zhaowei on 2016/6/6 0006.
 */
public class HomeBannerDetial extends BaseSwipeBackCompatActivity {


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
        setTitle("详情");
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
        if (getIntent().getStringExtra("TYPE").equals("1")){
            webView.loadUrl(UrlApi.ip +"Home/bannershow?"+"id="+getIntent().getStringExtra("banner_id"));
            Log.d("HomeBannerDetial",UrlApi.ip +"Home/bannershow?"+"id="+getIntent().getStringExtra("banner_id"));
        }else if(getIntent().getStringExtra("TYPE").equals("2")){
            webView.loadUrl(getIntent().getStringExtra("ADUrl"));
        }
    }

}