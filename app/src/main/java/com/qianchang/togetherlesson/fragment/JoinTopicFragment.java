package com.qianchang.togetherlesson.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.AttachFragment;
import com.qianchang.togetherlesson.baseAdapter.JoinAdapter;
import com.qianchang.togetherlesson.baseAdapter.TopicAdapter;
import com.qianchang.togetherlesson.bean.ClubListBean;
import com.qianchang.togetherlesson.bean.MyJoinTopicBean;
import com.qianchang.togetherlesson.bean.ZiCommentBean;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.qianchang.togetherlesson.utils.GetToast;
import com.qianchang.togetherlesson.widget.XListView;
import com.youth.xframe.widget.XToast;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**  我参与的话题列表
 * Created by admin on 2017/6/11.
 */

public class JoinTopicFragment extends AttachFragment implements XListView.IXListViewListener {
    View view;
    private String ispage;
    private int nextpage = 1;
    private XListView pullLoadMoreRecyclerView;
    private List<MyJoinTopicBean> relaseList = new ArrayList<>();
    private JoinAdapter topicAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_release_topic, container, false);
        pullLoadMoreRecyclerView = (XListView) view.findViewById(R.id.pullLoadMoreRecyclerView);
        pullLoadMoreRecyclerView.setPullLoadEnable(true);
        topicAdapter = new JoinAdapter(getActivity(), relaseList);
        pullLoadMoreRecyclerView.setAdapter(topicAdapter);
        pullLoadMoreRecyclerView.setXListViewListener(this);
        getDate();
        return view;
    }

    private void getDate() {
        MyResponseHandler handler = new MyResponseHandler(getActivity(), false) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    Logger.json(responseString);
                    if (jsonObject.optString("message").equals("1")) {

                        JSONArray bannerjsonArray = jsonObject.optJSONArray("clublist");

                        for (int i = 0; i < bannerjsonArray.length(); i++) {
                            JSONObject object = bannerjsonArray.optJSONObject(i);
                            MyJoinTopicBean bannerBean = new MyJoinTopicBean();
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
                            JSONArray  jsonArray=new JSONArray(object.optString("pinglun"));
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject object2 = jsonArray.optJSONObject(j);
                                ZiCommentBean ziCommentBean = new ZiCommentBean();
                                ziCommentBean.id = object2.optString("id");
                                ziCommentBean.uid = object2.optString("uid");
                                ziCommentBean.content = object2.optString("content");

                                ziCommentBean.posttime = object2.optString("posttime");
                                ziCommentBean.hid = object2.optString("hid");
                                ziCommentBean.type = object2.optString("type");

                                ziCommentBean.username = object2.optString("username");
                                ziCommentBean.avatar = object2.optString("avatar");
                                ziCommentBean.rename = object2.optString("rename");
                                bannerBean.ziCommentBeanList.add(ziCommentBean);
                            }
                            relaseList.add(bannerBean);
                        }
                        ispage = jsonObject.optString("isnextpage");
                        nextpage = jsonObject.optInt("nextpage");
                        if (ispage.equals("0")) {
                            GetToast.useString(getActivity(), "没有更多了...");
                            XToast.warning("没有更多了");
                        }
                        topicAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.myjoinClub(handler, MyApplication.P2PPreferences.getUid(), nextpage);
    }

    @Override
    public void onRefresh() {
        nextpage = 1;
        relaseList.clear();
        topicAdapter.notifyDataSetChanged();
        getDate();
        onLoad();
    }

    @Override
    public void onLoadMore() {
        if (ispage.equals("1")) {
            getDate();
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
}
