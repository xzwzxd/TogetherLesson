package com.qianchang.togetherlesson.activty;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.api.UrlApi;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.qianchang.togetherlesson.utils.GlideCircleTransform;
import com.qianchang.togetherlesson.utils.SharePopWindow;
import com.qianchang.togetherlesson.utils.WeiXinHelper;
import com.youth.xframe.widget.XToast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/21.
 */

public class NewsDetialActivity extends BaseSwipeBackCompatActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.picarr)
    ImageView picarr;
    @Bind(R.id.author)
    TextView author;
    @Bind(R.id.collectnum)
    TextView collectnum;
    @Bind(R.id.hits)
    TextView hits;
    private WebView webView;// web页面
    private WebSettings webSettings;// web页面设置
    private RequestManager glideRequest;

    public static String iscollect = "0"; //没有收藏 1 收藏
    private WeiXinHelper weiXinHelper;
    private SharePopWindow sharePopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        setTitle("详情");
        initView();
        initInfo();
        getDate();
    }

    private void initView() {

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
        weiXinHelper = new WeiXinHelper(NewsDetialActivity.this);

        TextView textView = (TextView) findViewById(R.id.tv_right);
        textView.setBackgroundResource(R.mipmap.img_more);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sharePopWindow == null) {
                    sharePopWindow = new SharePopWindow(cnt, itemClickListener, "1");
                }
                if (!sharePopWindow.isShowing()) {
                    sharePopWindow.showAtLocation(findViewById(R.id.tv_right), Gravity.BOTTOM, 0, 0);
                }
            }
        });


        findViewById(R.id.shoucang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MyApplication.P2PPreferences.getUid().equals("")) {
                    XToast.warning("请登录后再收藏");
                } else {
                    if (iscollect.equals("0")) {

                        getCollection();
                        findViewById(R.id.shoucang).setClickable(false);

                    } else if (iscollect.equals("1")) {

                        XToast.warning("已收藏");
                    }
                }
            }
        });
        setShoucangButtonVisibility(View.VISIBLE);
        setRightButtonVisibility(View.VISIBLE);


    }

    private void getDate() {

        MyResponseHandler handler = new MyResponseHandler(this, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                Logger.json(responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    if (jsonObject.optString("message").equals("1")) {
                        JSONObject jsonObject1 = new JSONObject(jsonObject.optString("news"));

                        tvTitle.setText(jsonObject1.optString("title"));
                        author.setText(jsonObject1.optString("author"));
                        collectnum.setText(jsonObject1.optString("collectnum"));
                        hits.setText(jsonObject1.optString("hits"));

                        glideRequest = Glide.with(cnt);
                        glideRequest.load(UrlApi.ip_pic + jsonObject1.optString("picurl")).transform(new GlideCircleTransform(cnt)).into(picarr);
                        webView.loadUrl(jsonObject1.optString("link"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.fragment_newsshow(handler, getIntent().getStringExtra("id"), MyApplication.P2PPreferences.getUid());
    }

    private void getCollection() {

        MyResponseHandler handler = new MyResponseHandler(this, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                Logger.json(responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    if (jsonObject.optString("message").equals("1")) {
                        XToast.success(getIntent().getStringExtra("toast"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.collectnews(handler, MyApplication.P2PPreferences.getUid(), getIntent().getStringExtra("id"));
    }

    private View.OnClickListener itemClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.wechat:
                    weiXinHelper.sendPageToFriend("http://115.28.215.113:83/index.php/Api/Danye/share", "聚客", "", true, R.mipmap.ic_logo);
                    break;
                case R.id.circle:
                    weiXinHelper.sendPageToFCircle("http://115.28.215.113:83/index.php/Api/Danye/share", "聚客", "", false, R.mipmap.ic_logo);
                    break;
                case R.id.to_report: //举报

                    break;
            }
        }
    };

}
