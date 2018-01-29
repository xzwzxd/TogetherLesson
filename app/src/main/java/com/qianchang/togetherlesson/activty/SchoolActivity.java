package com.qianchang.togetherlesson.activty;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.baseAdapter.CommonAdapter;
import com.qianchang.togetherlesson.baseAdapter.ViewHolder;
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

public class SchoolActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.lv_school)
    ListView lvSchool;
    private List<String> mDatas = new ArrayList<>();
    private CommonAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        ButterKnife.bind(this);
        getBanner();
        mAdapter = new CommonAdapter<String>(cnt, R.layout.item_school, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.id_item_list_title, s);

            }
        };
        lvSchool.setAdapter(mAdapter);
        lvSchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyApplication.USER_SCHOOL = mDatas.get(i);
                finish();
            }
        });
    }

    private void getBanner() {
        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    if (jsonObject.optString("message").equals("1")) {

                        JSONArray bannerjsonArray = jsonObject.optJSONArray("schoollist");
                        for (int i = 0; i < bannerjsonArray.length(); i++) {
                            JSONObject object = bannerjsonArray.optJSONObject(i);
                            String bannerBean = new String();
                            bannerBean = object.optString("school");
                            mDatas.add(bannerBean);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.communitylist(handler);
    }


}
