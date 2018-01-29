package com.qianchang.togetherlesson.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.activty.NewsDetialActivity;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.AttachFragment;
import com.qianchang.togetherlesson.baseAdapter.SchoolNewsAdapter;
import com.qianchang.togetherlesson.bean.SchoolNewsNean;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**  学校 动态
 * Created by Administrator on 2017/4/22.
 */

public class SchoolTrendsFragment extends AttachFragment {
    View view;
    @Bind(R.id.id_recyclerview)
    ListView idRecyclerview;
    @Bind(R.id.ly_chat)
    LinearLayout lyChat;
    @Bind(R.id.ly_have_class)
    LinearLayout lyHaveClass;
    private SchoolNewsAdapter mAdapter;
    private List<SchoolNewsNean> newsBeanList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommend, container, false);
        ButterKnife.bind(this, view);
        getBanner();
        mAdapter=new SchoolNewsAdapter(getActivity(),newsBeanList);
        idRecyclerview.setAdapter(mAdapter);
        idRecyclerview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), NewsDetialActivity.class).putExtra("id",newsBeanList.get(i).id).putExtra("title",newsBeanList.get(i).title).putExtra("toast","收藏成功"));
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void getBanner() {
        MyResponseHandler handler = new MyResponseHandler(getActivity(), false) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                newsBeanList.clear();
                super.onSuccess(statusCode, headers, responseString);
                Logger.json(responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    if (jsonObject.optString("message").equals("1")) {

                        JSONArray bannerjsonArray = jsonObject.optJSONArray("schoolnews");
                        for (int i = 0; i < bannerjsonArray.length(); i++) {
                            JSONObject object = bannerjsonArray.optJSONObject(i);
                            SchoolNewsNean courseBean = new SchoolNewsNean();
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
        ServerData.schoolnews(handler, MyApplication.SCHOOL_ID);
    }

}
