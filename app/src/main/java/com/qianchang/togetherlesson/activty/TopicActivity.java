package com.qianchang.togetherlesson.activty;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.baseAdapter.TopicAdapter;
import com.qianchang.togetherlesson.bean.ClubListBean;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.qianchang.togetherlesson.widget.XListView;
import com.youth.xframe.widget.XToast;

import org.apache.commons.lang3.StringEscapeUtils;
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
 * Created by admin on 2017/6/6.
 */

public class TopicActivity extends BaseSwipeBackCompatActivity implements XListView.IXListViewListener {

    @Bind(R.id.btn_relase)
    Button btnRelase;
    private List<ClubListBean> clubListBeanList = new ArrayList<>();
    private String ispage;
    private int nextpage = 1;
    private XListView pullLoadMoreRecyclerView;
    private LinearLayout pagefragment_iv;
    private TopicAdapter topicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);
        setTitle("话题");
        findViewById(R.id.tv_right).setBackgroundResource(R.mipmap.img_topic_avuto);
        setRightButtonVisibility(View.VISIBLE);
        findViewById(R.id.tv_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!MyApplication.P2PPreferences.getUid().equals("")) {
                    startActivity(new Intent(cnt, MyReportActivity.class));
                } else {
                    XToast.warning("请登录");
                }
            }
        });
        pullLoadMoreRecyclerView = (XListView) findViewById(R.id.pullLoadMoreRecyclerView);
        pullLoadMoreRecyclerView.setPullLoadEnable(true);
        pagefragment_iv = (LinearLayout) LayoutInflater.from(cnt).inflate(R.layout.header_topic, null);
        pullLoadMoreRecyclerView.addHeaderView(pagefragment_iv);
        topicAdapter = new TopicAdapter(cnt, clubListBeanList);
        pullLoadMoreRecyclerView.setAdapter(topicAdapter);
        pullLoadMoreRecyclerView.setXListViewListener(this);

        getBanner();

    }

    private void getBanner() {
        MyResponseHandler handler = new MyResponseHandler(cnt, false) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    Logger.json(responseString);
                    if (jsonObject.optString("message").equals("1")) {
                        ispage = jsonObject.optString("isnextpage");
                        nextpage = jsonObject.optInt("nextpage");

                        JSONArray bannerjsonArray = jsonObject.optJSONArray("clublist");

                        for (int i = 0; i < bannerjsonArray.length(); i++) {
                            JSONObject object = bannerjsonArray.optJSONObject(i);
                            ClubListBean bannerBean = new ClubListBean();
                            bannerBean.id = object.optString("id");
                            bannerBean.posttime = object.optString("posttime");
                            bannerBean.content = object.optString("content");
                            bannerBean.pinglunnum = object.optString("pinglunnum");
                            bannerBean.username = object.optString("username");
                            bannerBean.avatar = object.optString("avatar");
                            bannerBean.picarr = object.optString("picarr");
                            String str_marks = object.optString("picarr");
                            if (!str_marks.equals("")) {
                                String[] strarray = str_marks.substring(1, str_marks.length() - 1).split(",");
                                for (int j = 0; j < strarray.length; j++) {
                                    String tag = strarray[j];
                                    try {
                                        String s2 = StringEscapeUtils.unescapeJava(tag.substring(1, tag.length() - 1));
                                        bannerBean.stringList.add(s2);
                                    } catch (Exception e) {
                                    }
                                }
                            }
                            clubListBeanList.add(bannerBean);
                        }
                        topicAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.clublist(handler, nextpage);
    }


    @Override
    public void onRefresh() {
        nextpage = 1;
        clubListBeanList.clear();
        topicAdapter.notifyDataSetChanged();
        getBanner();
        onLoad();
    }

    @Override
    public void onLoadMore() {
        if (ispage.equals("1")) {
            getBanner();
        } else if (ispage.equals("0")) {
            onLoad();
        }
    }

    /**
     * 停止刷新，
     */
    private void onLoad() {
        pullLoadMoreRecyclerView.stopRefresh();
        pullLoadMoreRecyclerView.stopLoadMore();
        pullLoadMoreRecyclerView.setRefreshTime("刚刚");
    }


    @OnClick(R.id.btn_relase)
    public void onViewClicked() {


        if (!MyApplication.P2PPreferences.getUid().equals("")) {
            startActivity(new Intent(cnt, ToRelaseActivity.class));
        } else {
            XToast.warning("请登录");
        }
    }
}
