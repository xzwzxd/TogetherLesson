package com.qianchang.togetherlesson.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.baseAdapter.NewsAdapter;
import com.qianchang.togetherlesson.bean.NewsBean;
import com.qianchang.togetherlesson.bean.ThemeBean;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.qianchang.togetherlesson.widget.XListView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by lizhaotailang on 2016/3/21.
 * 主题日报下viewpager部分
 */
public class PageFragment extends Fragment implements XListView.IXListViewListener {
    View view;
    public static final String ARGS_PAGE = "args_page";
    private int pages;
    private final String TAG = "PageFragment";
    private List<ThemeBean> title = new ArrayList<ThemeBean>();
    private List<NewsBean> newsBeanList = new ArrayList<>();
    private XListView pullLoadMoreRecyclerView;
    private NewsAdapter mAdapter;  //内容

    private String allSchoolispage;
    private String allSchoolnextpage ="1" ;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pages = getArguments().getInt(ARGS_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.theme_page, container, false);
        pullLoadMoreRecyclerView = (XListView) view.findViewById(R.id.pullLoadMoreRecyclerView);
        pullLoadMoreRecyclerView.setPullLoadEnable(true);

        initpuLLFresh();
        title.clear();
        newsBeanList.clear();
        try {
            getTitle();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void initpuLLFresh() {
        mAdapter = new NewsAdapter(getActivity(), newsBeanList);
        pullLoadMoreRecyclerView.setAdapter(mAdapter);
        pullLoadMoreRecyclerView.setXListViewListener(this);
    }


    private void  getTitle(){

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
                            bannerBean.indextype = object.optString("indextype");
                            title.add(bannerBean);
                        }
                        getContent();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        ServerData.newstype(handler);
    }
    private void getContent() {
        MyResponseHandler handler = new MyResponseHandler(getActivity(), true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                Logger.json(responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    if (jsonObject.optString("message").equals("1")) {
                        allSchoolispage = jsonObject.optString("isnextpage");
                        allSchoolnextpage = jsonObject.optString("nextpage");
                        JSONArray bannerjsonArray = jsonObject.optJSONArray("newslist");
                        for (int i = 0; i < bannerjsonArray.length(); i++) {
                            JSONObject object = bannerjsonArray.optJSONObject(i);
                            NewsBean courseBean = new NewsBean();
                            courseBean.id = object.optString("id");
                            courseBean.title = object.optString("title");
                            courseBean.hits = object.optString("hits");
                            courseBean.collectnum = object.optString("collectnum");
                            courseBean.author = object.optString("author");
                            courseBean.picarr = object.optString("picarr");
                            courseBean.picurl = object.optString("picurl");
                            newsBeanList.add(courseBean);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Log.e("xzw",title.get(pages - 1).indextype);
        ServerData.fragment_news(handler,title.get(pages - 1).indextype,allSchoolnextpage);
    }


    /** 停止刷新， */
    private void onLoad() {
        pullLoadMoreRecyclerView.stopRefresh();
        pullLoadMoreRecyclerView.stopLoadMore();
        pullLoadMoreRecyclerView.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh() {

        mAdapter.notifyDataSetChanged();
        newsBeanList.clear();
        allSchoolnextpage = "1";
        getContent();
        onLoad();
    }

    @Override
    public void onLoadMore() {
        if (allSchoolispage.equals("1")) {
            getContent();
        } else if (allSchoolispage.equals("0")) {
            onLoad();
        }

    }
}
