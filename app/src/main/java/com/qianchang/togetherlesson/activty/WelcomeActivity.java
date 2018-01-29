package com.qianchang.togetherlesson.activty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.qianchang.togetherlesson.MainActivity;
import com.qianchang.togetherlesson.R;


/**
 * Created by Administrator on 2015/11/20.
 */
public class WelcomeActivity extends AppCompatActivity {


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.start_activity);
        new Handler() {

            @Override
            public void handleMessage(Message msg) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();

            }
        }.sendEmptyMessageDelayed(0, 3000);


    }

}
