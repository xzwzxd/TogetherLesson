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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/18.
 */

public class AgeActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.lv_school)
    ListView lvSchool;
    private List<String> mDatas = new ArrayList<>(Arrays.asList("大一",
            "大二",
            "大三", "大四", "大五", "研究生", "硕士", "博士", "参加工作 "));
    private CommonAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        ButterKnife.bind(this);
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
                MyApplication.USER_CLASS = mDatas.get(i);
                finish();
            }
        });
    }


}
