package com.qianchang.togetherlesson.activty;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.baseAdapter.MyComposeAdapter;
import com.qianchang.togetherlesson.bean.MyComposeBean;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：xiezhaowei
 * 时间：2017/11/27:15:25
 * 邮箱：1622955518@qq.com
 */
public class MyComposeActivity extends BaseSwipeBackCompatActivity {
    private RecyclerView mRecyclerView;
    private List<MyComposeBean.UsercouponBean> myComposeBeanList = new ArrayList<>();
    private MyComposeAdapter myComposeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_school_pic);
        setTitle("优惠券");
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_school_pic);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(cnt));
        initData();
    }

    private void initData() {
        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                Log.d("xzw", responseString);
                MyComposeBean myComposeBean = MyComposeBean.objectFromData(responseString);
                myComposeBeanList = myComposeBean.getUsercoupon();
                myComposeAdapter = new MyComposeAdapter(R.layout.item_compose, myComposeBeanList);
                myComposeAdapter.openLoadAnimation();
                mRecyclerView.setAdapter(myComposeAdapter);
            }
        };
        ServerData.mycoupon(handler, MyApplication.P2PPreferences.getUid());
    }
}
