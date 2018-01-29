//package com.qianchang.togetherlesson.activty;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.ApplicationInfo;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.orhanobut.logger.Logger;
//import com.qianchang.togetherlesson.R;
//import com.qianchang.togetherlesson.app.MyApplication;
//import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
//import com.qianchang.togetherlesson.fragment.SchoolCourseFragment;
//import com.qianchang.togetherlesson.fragment.SchoolDetialFragment;
//import com.qianchang.togetherlesson.fragment.SchoolPicFragment;
//import com.qianchang.togetherlesson.fragment.SchoolTrendsFragment;
//import com.qianchang.togetherlesson.http.api.MyResponseHandler;
//import com.qianchang.togetherlesson.http.api.ServerData;
//import com.qianchang.togetherlesson.http.api.UrlApi;
//import com.qianchang.togetherlesson.http.json.JSONObjectEx;
//import com.qianchang.togetherlesson.utils.ChatPopWindow;
//import com.qianchang.togetherlesson.utils.SharePopWindow;
//import com.qianchang.togetherlesson.utils.WeiXinHelper;
//import com.youth.xframe.widget.XToast;
//
//import org.apache.http.Header;
//import org.json.JSONException;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
///**
// * Created by Administrator on 2017/4/22.
// */
//
//public class SchoolDetialActivity extends BaseSwipeBackCompatActivity {
//
//    @Bind(R.id.tabLayout)
//    TabLayout tabLayout;
//    @Bind(R.id.viewPager)
//    ViewPager viewPager;
//    @Bind(R.id.pic_school_detial)
//    ImageView picSchoolDetial;
//    @Bind(R.id.tv_title)
//    TextView tvTitle;
//    @Bind(R.id.tv_collect_num)
//    TextView tvCollectNum;
//    @Bind(R.id.tv_adddress)
//    TextView tvAdddress;
//    @Bind(R.id.ly_chat)
//    LinearLayout lyChat;
//    @Bind(R.id.ly_sign_up)
//    LinearLayout lySignUp;
//
//    ChatPopWindow chatPopWindow;
//    String erCode_pic;
//    String phone = "";//二维码
//    String qq = "";//二维码
//
//
//    public static String iscollect = "0"; //没有收藏 1 收藏
//    private WeiXinHelper weiXinHelper;
//    private SharePopWindow sharePopWindow;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_school_detial);
//        ButterKnife.bind(this);
//        setTitle("学校详情");
//        initViewPagerAndTabs();
//        getDate();
//
//        TextView textView = (TextView) findViewById(R.id.tv_right);
//        textView.setBackgroundResource(R.mipmap.img_more);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (sharePopWindow == null) {
//                    sharePopWindow = new SharePopWindow(cnt, itemClickListenerpop, "1");
//                }
//                if (!sharePopWindow.isShowing()) {
//                    sharePopWindow.showAtLocation(findViewById(R.id.tv_right), Gravity.BOTTOM, 0, 0);
//                }
//            }
//        });
//
//        findViewById(R.id.shoucang).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (MyApplication.P2PPreferences.getUid().equals("")) {
//                    XToast.warning("请登录后再收藏");
//                } else {
//                    if (iscollect.equals("0")) {
//
//                        getCollection();
//                        findViewById(R.id.shoucang).setClickable(false);
//
//                    } else if (iscollect.equals("1")) {
//
//                        XToast.warning("已收藏");
//                    }
//                }
//            }
//        });
//        setShoucangButtonVisibility(View.VISIBLE);
//        setRightButtonVisibility(View.VISIBLE);
//        weiXinHelper = new WeiXinHelper(SchoolDetialActivity.this);
//    }
//
//    private void initViewPagerAndTabs() {
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
//        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new SchoolCourseFragment(), "课程");
//        adapter.addFragment(new SchoolDetialFragment(), "简介");
//        adapter.addFragment(new SchoolPicFragment(), "相册");
//
//        adapter.addFragment(new SchoolTrendsFragment(), "动态");
//        viewPager.setAdapter(adapter);
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        tabLayout.setupWithViewPager(viewPager);
//    }
//
//    @OnClick({R.id.ly_chat, R.id.ly_sign_up})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.ly_chat://咨询
//
//                if (chatPopWindow == null) {
//                    chatPopWindow = new ChatPopWindow(cnt, itemClickListener, getIntent().getStringExtra("pic_Url"), getIntent().getStringExtra("qqqun"), getIntent().getStringExtra("school_name"));
//                }
//                if (!chatPopWindow.isShowing()) {
//                    chatPopWindow.showAtLocation(findViewById(R.id.tv_right), Gravity.BOTTOM, 0, 0);
//                }
//
//                break;
//            case R.id.ly_sign_up://报名
//
//                android.app.AlertDialog.Builder ab = new android.app.AlertDialog.Builder(cnt);
//                ab.setTitle("确定报名？");
//                ab.setNegativeButton("取消",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//                ab.setPositiveButton("确定",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                postAnd();
//                            }
//                        });
//                ab.create().show();
//                break;
//        }
//    }
//
//    static class PagerAdapter extends FragmentPagerAdapter {
//
//        private final List<Fragment> mFragments = new ArrayList<>();
//        private final List<String> mFragmentTitles = new ArrayList<>();
//
//        public PagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragments.add(fragment);
//            mFragmentTitles.add(title);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragments.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitles.get(position);
//        }
//    }
//
//    private void getDate() {
//        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//
//                super.onSuccess(statusCode, headers, responseString);
//                try {
//                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
//                    Logger.json(responseString);
//                    if (jsonObject.optString("message").equals("1")) {
//                        JSONObjectEx jsonObject1 = new JSONObjectEx(jsonObject.optString("trainschool"));
//                        jsonObject1.optString("id");
//                        Glide.with(cnt).load(UrlApi.ip_pic + jsonObject1.optString("picurl")).placeholder(R.mipmap.zhanweitu5).into(picSchoolDetial);
//                        tvTitle.setText(jsonObject1.optString("title"));
//                        tvCollectNum.setText(jsonObject1.optString("collectnum"));
//                        tvAdddress.setText(jsonObject1.optString("address"));
//                        jsonObject1.optString("flag");
//
//
//                        erCode_pic = jsonObject1.optString("picurl2");
//                        phone = jsonObject1.optString("phone");
//                        qq = jsonObject.optString("qq");
//                        if (jsonObject.optString("iscollect").equals("0")) {  //是否收藏1为已收藏，0为微收藏
//                            findViewById(R.id.shoucang).setBackgroundResource(R.mipmap.img_collection);
//                        } else {
//                            findViewById(R.id.shoucang).setBackgroundResource(R.mipmap.shoucang_nomal);
//                        }
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        };
//        ServerData.trainschoolshow(handler, MyApplication.SCHOOL_ID, MyApplication.P2PPreferences.getUid());
//    }
//
//    private void postAnd() {
//        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//
//                super.onSuccess(statusCode, headers, responseString);
//                Logger.json(responseString);
//                try {
//                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
//                    if (jsonObject.optString("message").equals("1")) {
//                        XToast.success("报名成功");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        ServerData.apply(handler, MyApplication.SCHOOL_ID, MyApplication.P2PPreferences.getUid());
//    }
//
//
//    private void getCollection() {
//
//        MyResponseHandler handler = new MyResponseHandler(this, true) {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                super.onSuccess(statusCode, headers, responseString);
//                Logger.json(responseString);
//                try {
//                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
//                    if (jsonObject.optString("message").equals("1")) {
//                        findViewById(R.id.shoucang).setBackgroundResource(R.mipmap.img_collection);
//                        XToast.success(getIntent().getStringExtra("toast"));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        ServerData.collectschool(handler, MyApplication.P2PPreferences.getUid(), MyApplication.SCHOOL_ID);
//    }
//
//    private View.OnClickListener itemClickListener = new View.OnClickListener() {
//
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.ly_chat_email:
//
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    if (chatPopWindow == null) {
//                    } else {
//                        chatPopWindow.dismiss();
//                    }
//                    break;
//                case R.id.ly_chat_to_person:
//
//                    if (checkApkExist(cnt, "com.tencent.mobileqq")) {
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + qq + "&version=1")));
//                    } else {
//                        XToast.warning("本机未安装QQ应用");
//                    }
//                    break;
//                case R.id.ly_chat_to_group:
//
//                    startActivity(new Intent(cnt, ErCodeActivity.class).putExtra("erCode_pic", erCode_pic));
//                    if (chatPopWindow == null) {
//                    } else {
//                        chatPopWindow.dismiss();
//                    }
//                    break;
//            }
//        }
//    };
//
//
//    public boolean checkApkExist(Context context, String packageName) {
//        if (packageName == null || "".equals(packageName))
//            return false;
//        try {
//            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
//                    PackageManager.GET_UNINSTALLED_PACKAGES);
//            return true;
//        } catch (PackageManager.NameNotFoundException e) {
//            return false;
//        }
//    }
//
//    private View.OnClickListener itemClickListenerpop = new View.OnClickListener() {
//
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.wechat:
//                    weiXinHelper.sendPageToFriend("http://115.28.215.113:83/index.php/Api/Danye/share", "聚客", "", true, R.mipmap.ic_logo);
//                    break;
//                case R.id.circle:
//                    weiXinHelper.sendPageToFCircle("http://115.28.215.113:83/index.php/Api/Danye/share", "聚客", "", false, R.mipmap.ic_logo);
//                    break;
//                case R.id.to_report: //举报
//
//                    break;
//            }
//        }
//    };
//}
