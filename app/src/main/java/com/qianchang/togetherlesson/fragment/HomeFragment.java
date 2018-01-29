package com.qianchang.togetherlesson.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.activty.ClassesListActivity;
import com.qianchang.togetherlesson.activty.HistorySearchActivity;
import com.qianchang.togetherlesson.activty.HomeBannerDetial;
import com.qianchang.togetherlesson.activty.NewsDetialActivity;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.AttachFragment;
import com.qianchang.togetherlesson.bean.IndexBean;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.api.UrlApi;
import com.qianchang.togetherlesson.simpleAdapter.HomeCourseAdapter;
import com.qianchang.togetherlesson.simpleAdapter.HomeNewsAdapter;
import com.qianchang.togetherlesson.utils.XBannerLayout;
import com.uuch.adlibrary.AdConstant;
import com.uuch.adlibrary.AdManager;
import com.uuch.adlibrary.bean.AdInfo;
import com.uuch.adlibrary.utils.DisplayUtil;
import com.youth.xframe.widget.XToast;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2016/10/9.
 */
public class HomeFragment extends AttachFragment {
    private View view;
    private List<AdInfo> advList = null;
    private AdInfo adInfo;

    private IndexBean indexBean;
    private List<IndexBean.BannerlistBean> bannerlistBeanList = new ArrayList<>();
    private List<IndexBean.CourseslistBean> courseslistBeanList = new ArrayList<>();
    private List<IndexBean.NewslistBean> newslistBeanList = new ArrayList<>();
    private RecyclerView mRecyclerView, rv_course;
    private XBannerLayout bannerLayout;
    private HomeNewsAdapter homeAdapter;
    private HomeCourseAdapter courseAdapter;

    private TextView er_code;
    private LinearLayout ly_search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initDisplayOpinion();  // 初始化开屏广告
        initView();
        getDate();
        return view;
    }

    private void initView() {

        Fresco.initialize(getActivity());
        advList = new ArrayList<>();
        adInfo = new AdInfo();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

          view.findViewById(R.id.er_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XToast.warning("其他地区暂未开通");
            }
        });
         view.findViewById(R.id.ly_search).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(getActivity(), HistorySearchActivity.class));
             }
         });
    }

    private void initlis() {
        homeAdapter = new HomeNewsAdapter(R.layout.item_home, newslistBeanList);
        homeAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(homeAdapter);

        View top = getActivity().getLayoutInflater().inflate(R.layout.home_header, (ViewGroup) mRecyclerView.getParent(), false);
        homeAdapter.addHeaderView(top);
        bannerLayout = (XBannerLayout) top.findViewById(R.id.banner);

        rv_course = (RecyclerView) top.findViewById(R.id.rv_course);
        rv_course.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        courseAdapter = new HomeCourseAdapter(R.layout.item_course, courseslistBeanList);
        courseAdapter.openLoadAnimation();
        rv_course.setAdapter(courseAdapter);


        bannerLayout.setViewUrls(bannerlistBeanList);
        final String AD_uUR = indexBean.getIndexpicurl();   //首页广告连接
        if (AD_uUR.equals("")) {

        } else {
            adInfo.setActivityImg(UrlApi.ip_pic + AD_uUR);
            advList.add(adInfo);
            AdManager adManager = new AdManager(getActivity(), advList);
            adManager.setOnImageClickListener(new AdManager.OnImageClickListener() {
                @Override
                public void onImageClick(View view, AdInfo advInfo) {
                    Intent intent = new Intent(getActivity(), HomeBannerDetial.class);
                    intent.putExtra("ADUrl", AD_uUR);
                    intent.putExtra("TYPE", "2");
                    startActivity(intent);
                }
            })
                    .setPadding(100)
                    .setWidthPerHeight(0.5f)
                    .showAdDialog(AdConstant.ANIM_UP_TO_DOWN);
        }

        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), NewsDetialActivity.class).putExtra("id", newslistBeanList.get(position).getId()).putExtra("title", newslistBeanList.get(position).getTitle()).putExtra("toast", "收藏成功"));
            }
        });
        courseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyApplication.IS_SCHOOL_VISBLE = String.valueOf(position);
                startActivity(new Intent(getContext(), ClassesListActivity.class).putExtra("title", courseslistBeanList.get(position).getTitle()).putExtra("id", courseslistBeanList.get(position).getId()));
            }
        });
        //添加监听事件
        bannerLayout.setOnBannerItemClickListener(new XBannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), HomeBannerDetial.class);
                intent.putExtra("banner_id", bannerlistBeanList.get(position).getId());
                intent.putExtra("TYPE", "1");
                startActivity(intent);
            }
        });
    }

    private void getDate() {
        MyResponseHandler handler = new MyResponseHandler(getActivity(), true) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);

                Logger.json(responseString);
                indexBean = new IndexBean().objectFromData(responseString);
                bannerlistBeanList = indexBean.getBannerlist();
                courseslistBeanList = indexBean.getCourseslist();
                newslistBeanList = indexBean.getNewslist();
                initlis();
            }
        };
        ServerData.get_banner(handler);
    }

    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getActivity().getApplicationContext(), dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(getActivity().getApplicationContext(), dm.heightPixels);
    }

}
