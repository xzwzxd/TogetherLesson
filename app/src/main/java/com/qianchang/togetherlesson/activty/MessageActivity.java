package com.qianchang.togetherlesson.activty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.baseAdapter.CommonAdapter;
import com.qianchang.togetherlesson.baseAdapter.ViewHolder;
import com.qianchang.togetherlesson.bean.MeaageBean;
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

/**
 * Created by Administrator on 2017/5/18.
 */

public class MessageActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.lv_school)
    ListView lvSchool;
    private CommonAdapter<MeaageBean> mAdapter;
    private List<MeaageBean>  courseBeanlist=new  ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        ButterKnife.bind(this);
        setTitle("消息");

        if (!MyApplication.P2PPreferences.getUid().equals("")){
            message();
        }
        mAdapter = new CommonAdapter<MeaageBean>(cnt, R.layout.item_message, courseBeanlist) {
            @Override
            protected void convert(ViewHolder holder, MeaageBean s, int position) {
                holder.setText(R.id.title, s.title);
                holder.setText(R.id.time, s.posttime);
                holder.setText(R.id.des, s.content);
            }
        };
        lvSchool.setAdapter(mAdapter);

        lvSchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(cnt,MessageDetialActivity.class).putExtra("id",courseBeanlist.get(i).id).putExtra("title",courseBeanlist.get(i).title));
            }
        });
    }
    /****
     * 获取banner
     */
    private void message() {
        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    Logger.json(responseString);

                        JSONArray bannerjsonArray = jsonObject.optJSONArray("data");
                        for (int i = 0; i < bannerjsonArray.length(); i++) {
                            JSONObject object = bannerjsonArray.optJSONObject(i);
                            MeaageBean bannerBean = new MeaageBean();
                            bannerBean.id = object.optString("id");
                            bannerBean.title = object.optString("title");
                            bannerBean.posttime = object.optString("posttime");
                            bannerBean.state = object.optString("state");
                            bannerBean.content = object.optString("content");
                            courseBeanlist.add(bannerBean);
                        }
                        Logger.d(courseBeanlist.size());
                        mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.message(handler, MyApplication.P2PPreferences.getUid());
    }

}
