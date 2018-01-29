package com.qianchang.togetherlesson.activty;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.bean.SchoolDetialBean;
import com.qianchang.togetherlesson.fragment.SchoolCourseFragment;
import com.qianchang.togetherlesson.fragment.SchoolDetialFragment;
import com.qianchang.togetherlesson.fragment.SchoolPicFragment;
import com.qianchang.togetherlesson.fragment.SchoolTrendsFragment;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.api.UrlApi;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.qianchang.togetherlesson.utils.ChatPopWindow;
import com.qianchang.togetherlesson.utils.GlideCircleTransform;
import com.qianchang.togetherlesson.utils.WeiXinHelper;
import com.youth.xframe.widget.XToast;

import org.apache.http.Header;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/22.
 */

public class SchoolDetialActivity1 extends AppCompatActivity {


    @Bind(R.id.iv_cover_bg)
    ImageView ivCoverBg;
    @Bind(R.id.tv_focus_num)
    TextView tvFocusNum;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.iv_head)
    ImageView ivHead;
    @Bind(R.id.tv_bold_title)
    TextView tvBoldTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tab_view_pager)
    SmartTabLayout tabViewPager;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.layout_main_content)
    CoordinatorLayout layoutMainContent;
    @Bind(R.id.ly_chat)
    LinearLayout lyChat;
    @Bind(R.id.ly_sign_up)
    LinearLayout lySignUp;

    private AppBarState mState;
    public boolean isBack = true;

    private enum AppBarState {
        EXPANDED,
        COLLAPSED,
        MIDDLE
    }


    private SchoolDetialBean schoolDetialBean;
    private SchoolDetialBean.TrainschoolBean trainschoolBean;
    private RequestManager glideRequest;

    private WeiXinHelper weiXinHelper;
    private ChatPopWindow chatPopWindow;
    private String phone, qq;
    private String erCode_pic;

    private String school_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detial1);
        ButterKnife.bind(this);

        isBack = true;
        initToolB();
        initViewPagerAndTabs();
        getDate();
        initUi(false,school_title, "2");
        initListener();

    }

    private void initToolB() {
        setSupportActionBar(toolbar);
        setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (isBack) {
            toolbar.setNavigationIcon(R.drawable.title_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        weiXinHelper = new WeiXinHelper(SchoolDetialActivity1.this);
    }


    private void getDate() {
        MyResponseHandler handler = new MyResponseHandler(SchoolDetialActivity1.this, false) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                Logger.json(responseString);

                schoolDetialBean = new SchoolDetialBean().objectFromData(responseString);
                trainschoolBean = schoolDetialBean.getTrainschool();
                tvDescription.setText(trainschoolBean.getAddress());
                erCode_pic = trainschoolBean.getPicurl2();
                phone = trainschoolBean.getPhone();
                qq = trainschoolBean.getQq();
                school_title=trainschoolBean.getTitle();
                tvBoldTitle.setText(school_title);
                tvFocusNum.setText(school_title);
                glideRequest = Glide.with(SchoolDetialActivity1.this);
                glideRequest.load(UrlApi.ip_pic + trainschoolBean.getPicurl()).placeholder(R.mipmap.zhanweitu5).transform(new GlideCircleTransform(SchoolDetialActivity1.this)).into(ivHead);
            }
        };
        ServerData.trainschoolshow(handler, MyApplication.SCHOOL_ID, MyApplication.P2PPreferences.getUid());
    }

    private void initViewPagerAndTabs() {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SchoolCourseFragment(), "课程");
        adapter.addFragment(new SchoolDetialFragment(), "简介");
        adapter.addFragment(new SchoolPicFragment(), "相册");
        adapter.addFragment(new SchoolTrendsFragment(), "动态");
        viewPager.setAdapter(adapter);
        tabViewPager.setViewPager(viewPager);

    }

    @OnClick({R.id.ly_chat, R.id.ly_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_chat://咨询

                if (chatPopWindow == null) {
                    chatPopWindow = new ChatPopWindow(SchoolDetialActivity1.this, itemClickListener, getIntent().getStringExtra("pic_Url"), getIntent().getStringExtra("qqqun"), getIntent().getStringExtra("school_name"));
                }
                if (!chatPopWindow.isShowing()) {
                    chatPopWindow = new ChatPopWindow(SchoolDetialActivity1.this, itemClickListener, getIntent().getStringExtra("pic_Url"), getIntent().getStringExtra("qqqun"), getIntent().getStringExtra("school_name"));
                    chatPopWindow.showAtLocation(findViewById(R.id.ly_chat), Gravity.BOTTOM, 0, 0);
                }

                break;
            case R.id.ly_sign_up://报名

                android.app.AlertDialog.Builder ab = new android.app.AlertDialog.Builder(SchoolDetialActivity1.this);
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
                break;
        }
    }

    private void initListener() {
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (mState != AppBarState.EXPANDED) {
                        mState = AppBarState.EXPANDED;//修改状态标记为展开
                        tvBoldTitle.setVisibility(View.GONE);
                        toolbar.setNavigationIcon(R.mipmap.ic_action_back_white);
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (mState != AppBarState.COLLAPSED) {
                        mState = AppBarState.COLLAPSED;//修改状态标记为折叠
                        tvBoldTitle.setVisibility(View.VISIBLE);
                        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
                        tvBoldTitle.setText(school_title);
                    }
                } else {
                    if (mState != AppBarState.MIDDLE) {
                        if (mState == AppBarState.COLLAPSED) {
                            tvBoldTitle.setVisibility(View.GONE);
                            toolbar.setNavigationIcon(R.mipmap.ic_action_back_white);
                        }
                        mState = AppBarState.MIDDLE;//修改状态标记为中间
                    }
                }
            }
        });
    }

    public void initUi(boolean isShowBtn, String title, String imgUrl) {
        tvDescription.setVisibility(isShowBtn ? View.GONE : View.VISIBLE);
        Glide.with(SchoolDetialActivity1.this).load(R.mipmap.cover_default).into(ivCoverBg);

    }


    static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
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

                    if (checkApkExist(SchoolDetialActivity1.this, "com.tencent.mobileqq")) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + qq + "&version=1")));
                    } else {
                        XToast.warning("本机未安装QQ应用");
                    }
                    break;
                case R.id.ly_chat_to_group:

                    startActivity(new Intent(SchoolDetialActivity1.this, ErCodeActivity.class).putExtra("erCode_pic", erCode_pic));
                    if (chatPopWindow == null) {
                    } else {
                        chatPopWindow.dismiss();
                    }
                    break;
            }
        }
    };

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

    private View.OnClickListener itemClickListenerpop = new View.OnClickListener() {

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

    private void postAnd() {
        MyResponseHandler handler = new MyResponseHandler(SchoolDetialActivity1.this, true) {
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
        ServerData.apply(handler, MyApplication.SCHOOL_ID, MyApplication.P2PPreferences.getUid());
    }
}
