package com.qianchang.togetherlesson.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.activty.ConsultantActivty;
import com.qianchang.togetherlesson.activty.NewsDetialActivity;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.AttachFragment;
import com.qianchang.togetherlesson.baseAdapter.NewsAdapter;
import com.qianchang.togetherlesson.bean.NewsBean;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.api.UrlApi;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/22.
 */

public class SchoolDetialFragment extends AttachFragment {
    View view;
    private WebView webView;// web页面
    private WebSettings webSettings;// web页面设置

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.webview, container, false);

        webView = (WebView) view.findViewById(R.id.webview);
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
        webView.loadUrl(UrlApi.ip + "Home/trainschoolweb?" + "id=" + MyApplication.SCHOOL_ID);
        Log.d("xzw",UrlApi.ip + "Home/trainschoolweb?" + "id=" + MyApplication.SCHOOL_ID);
        return view;
    }

}
