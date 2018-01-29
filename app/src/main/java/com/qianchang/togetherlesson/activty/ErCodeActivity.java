package com.qianchang.togetherlesson.activty;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.http.api.UrlApi;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/21.
 */

public class ErCodeActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.user_icon)
    ImageView userIcon;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.user_num)
    TextView userNum;
    @Bind(R.id.user_ercode)
    ImageView userErcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_er_code);
        setTitle("二维码");
        ButterKnife.bind(this);
        Glide.with(cnt).load(UrlApi.ip_pic + getIntent().getStringExtra("erCode_pic")).into(userErcode);
    }


    }
