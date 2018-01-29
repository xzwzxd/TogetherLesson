package com.qianchang.togetherlesson.activty;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.baseAdapter.CourseActivityAdapter;
import com.qianchang.togetherlesson.baseAdapter.CourseComposeAdapter;
import com.qianchang.togetherlesson.bean.CourseCopuse;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.api.UrlApi;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.qianchang.togetherlesson.utils.ChatPopWindow;
import com.qianchang.togetherlesson.utils.GlideCircleTransform;
import com.youth.xframe.widget.XToast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/6/4.
 */

public class CouseDetialActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.tv_class)
    TextView tvClass;
    @Bind(R.id.tv_timelast)
    TextView tvTimelast;
    @Bind(R.id.tv_mone)
    TextView tvMone;
    @Bind(R.id.tv_content)
    WebView tvContent;
    ChatPopWindow chatPopWindow;
    @Bind(R.id.ly_chat)
    LinearLayout lyChat;
    @Bind(R.id.ly_sign_up)
    LinearLayout lySignUp;
    String qq, qqqun;
    @Bind(R.id.img_school)
    ImageView imgSchool;
    @Bind(R.id.tv_school_name)
    TextView tvSchoolName;
    @Bind(R.id.ly_to_school)
    LinearLayout lyToSchool;
    private RequestManager glideRequest;
    String schoolid;
    String isapply;
    String pic_Url = "";
    String school_name = "";


    String erCode_pic = ""; //二维码
    String phone = "";//二维码
    private WebView webView;// web页面
    private WebSettings webSettings;// web页面设置


    private List<CourseCopuse.LessonBean.CouponBean> courseCopuseList = new ArrayList<>();
    private List<CourseCopuse.LessonBean.ActivityBean> activityBeanList = new ArrayList<>();

    protected CourseComposeAdapter courseComposeAdapter;
    private RecyclerView mRecyclerView;

    protected CourseActivityAdapter courseActivityAdapter;
    private RecyclerView rv_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detial);
        ButterKnife.bind(this);
        setTitle(getIntent().getStringExtra("title"));
        tvSchoolName.setText(getIntent().getStringExtra("title"));
        tvClass.setText(getIntent().getStringExtra("title"));
        webView = (WebView) findViewById(R.id.tv_content);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_coupon);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(cnt));

        courseComposeAdapter = new CourseComposeAdapter(R.layout.item_course_compse, courseCopuseList);
        courseComposeAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(courseComposeAdapter);
        courseComposeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                getCompose(courseCopuseList.get(position).id);
            }
        });

        rv_activity = (RecyclerView) findViewById(R.id.rv_activity);
        rv_activity.setLayoutManager(new LinearLayoutManager(cnt));
        courseActivityAdapter = new CourseActivityAdapter(R.layout.item_course_activity, courseCopuseList);
        courseActivityAdapter.openLoadAnimation();
        rv_activity.setAdapter(courseActivityAdapter);
        getDate();
    }

    private void getDate() {
        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);

                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    Logger.json(responseString);
                    if (jsonObject.optString("message").equals("1")) {

                        qq = jsonObject.optString("qq");
                        qqqun = jsonObject.optString("qqqun");
                        JSONObjectEx jsonObject1 = new JSONObjectEx(jsonObject.optString("lesson"));
                        jsonObject1.optString("author");

                        jsonObject1.optString("subject");
                        jsonObject1.optString("times");
                        jsonObject1.optString("period");
                        jsonObject1.optString("cost");
                        jsonObject1.optString("content");
                        jsonObject1.optString("schooltitle");
                        jsonObject1.optString("schoolpicurl");
                        schoolid = jsonObject1.optString("schoolid");

                        isapply = jsonObject1.optString("isapply");

                        if (isapply.endsWith("0")) {  //0为未报名
                            lySignUp.setBackgroundResource(R.color.colorPrimaryDark);
                        } else { //1为已报名
                            lySignUp.setBackgroundResource(R.drawable.apply_un_select);
                        }
                        MyApplication.SCHOOL_ID = schoolid;
                        school_name = jsonObject1.optString("schooltitle");
                        pic_Url = jsonObject1.optString("schoolpicurl");
                        glideRequest = Glide.with(cnt);
                        glideRequest.load(UrlApi.ip_pic + jsonObject1.optString("schoolpicurl")).placeholder(R.mipmap.zhanweitu5).transform(new GlideCircleTransform(cnt)).into(imgSchool);
                        tvTimelast.setText(jsonObject1.optString("period"));
                        tvMone.setText(jsonObject1.optString("cost"));
                        erCode_pic = jsonObject.optString("picurl2");
                        phone = jsonObject.optString("phone");

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
                        webView.loadUrl(jsonObject1.optString("content"));


                        JSONArray courseslistArray = jsonObject1.optJSONArray("coupon");
                        for (int i = 0; i < courseslistArray.length(); i++) {
                            JSONObject object = courseslistArray.optJSONObject(i);
                            CourseCopuse.LessonBean.CouponBean courseBean = new CourseCopuse.LessonBean.CouponBean();
                            courseBean.id = object.optString("id");
                            courseBean.title = object.optString("title");
                            courseCopuseList.add(courseBean);
                        }
                        courseComposeAdapter.notifyDataSetChanged();
                        JSONArray activitylistArray = jsonObject1.optJSONArray("activity");
                        for (int i = 0; i < activitylistArray.length(); i++) {
                            JSONObject object = activitylistArray.optJSONObject(i);
                            CourseCopuse.LessonBean.ActivityBean courseBean = new CourseCopuse.LessonBean.ActivityBean();
                            courseBean.id = object.optString("id");
                            courseBean.title = object.optString("title");
                            activityBeanList.add(courseBean);
                        }
                        courseActivityAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        ServerData.lessonshow(handler, getIntent().getStringExtra("id"));

    }

    @OnClick({R.id.ly_chat, R.id.ly_sign_up, R.id.ly_to_school})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_chat:

                if (chatPopWindow == null) {
                    chatPopWindow = new ChatPopWindow(cnt, itemClickListener, pic_Url, qqqun, school_name);
                }
                if (!chatPopWindow.isShowing()) {
                    chatPopWindow.showAtLocation(findViewById(R.id.tv_right), Gravity.BOTTOM, 0, 0);
                }
                break;
            case R.id.ly_sign_up:
                if (MyApplication.P2PPreferences.getUid().equals("")) {

                    XToast.warning("请登录后再报名");
                } else {
                    AlertDialog.Builder ab = new AlertDialog.Builder(cnt);
                    ab.setTitle("确定报名？");
                    ab.setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    ab.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    postAnd();

                                }
                            });
                    ab.create().show();
                }

                break;
            case R.id.ly_to_school:

                startActivity(new Intent(cnt, SchoolDetialActivity1.class).putExtra("id", schoolid).putExtra("qq", qq).putExtra("qqqun", qqqun).putExtra("pic_Url", pic_Url).putExtra("name", school_name));
                break;
        }
    }

    private View.OnClickListener itemClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ly_chat_email:

                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    if (chatPopWindow == null) {
                    } else {
                        chatPopWindow.dismiss();
                    }
                    break;
                case R.id.ly_chat_to_person:

                    if (checkApkExist(cnt, "com.tencent.mobileqq")) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + qq + "&version=1")));
                    } else {
                        XToast.warning("本机未安装QQ应用");
                    }
                    break;
                case R.id.ly_chat_to_group:

                    startActivity(new Intent(cnt, ErCodeActivity.class).putExtra("erCode_pic", erCode_pic));
                    if (chatPopWindow == null) {
                    } else {
                        chatPopWindow.dismiss();
                    }
                    break;
            }
        }
    };

    /*****
     * 报名
     */
    private void postAnd() {
        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                super.onSuccess(statusCode, headers, responseString);
                Logger.json(responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    if (jsonObject.optString("message").equals("1")) {
                        XToast.success("报名成功");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.lessonapply(handler, MyApplication.SCHOOL_ID, MyApplication.P2PPreferences.getUid());
    }

    /*****
     *  领取优惠券：
     */
    private void getCompose(String composeId) {
        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                super.onSuccess(statusCode, headers, responseString);
                Logger.json(responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    if (jsonObject.optString("message").equals("1")) {
                        XToast.success(jsonObject.optString("alert"));
                    } else if (jsonObject.optString("message").equals("2")) {
                        XToast.success(jsonObject.optString("alert"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.getcoupon(handler, MyApplication.P2PPreferences.getUid(), composeId);
    }

    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
