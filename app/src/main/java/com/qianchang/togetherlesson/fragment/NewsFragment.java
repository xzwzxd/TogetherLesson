package com.qianchang.togetherlesson.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.activty.HistorySearchActivity;
import com.qianchang.togetherlesson.baseAdapter.ThemePagerAdapter;
import com.qianchang.togetherlesson.bean.ThemeBean;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.youth.xframe.widget.XToast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/10/9.
 */
public class NewsFragment extends Fragment {

    View view;
    private List<ThemeBean> themes = new ArrayList<ThemeBean>();
    ViewPager pager;
    TabLayout tabLayout;
    ThemePagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);

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

        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        pager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        adapter = new ThemePagerAdapter(getFragmentManager(), getActivity(), themes);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);


        MyResponseHandler handler = new MyResponseHandler(getActivity(), true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                super.onSuccess(statusCode, headers, responseString);
                Logger.json(responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    Logger.json(responseString);
                    if (jsonObject.optString("message").equals("1")) {
                        JSONArray bannerjsonArray = jsonObject.optJSONArray("newstype");
                        for (int i = 0; i < bannerjsonArray.length(); i++) {
                            JSONObject object = bannerjsonArray.optJSONObject(i);
                            ThemeBean bannerBean = new ThemeBean();
                            bannerBean.title = object.optString("title");
                            themes.add(bannerBean);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.newstype(handler);
        return view;
    }
}
