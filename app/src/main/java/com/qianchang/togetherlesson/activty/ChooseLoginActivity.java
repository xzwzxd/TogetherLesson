package com.qianchang.togetherlesson.activty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.base.BaseActivity;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.utils.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by admin on 2017/2/23.
 */

public class ChooseLoginActivity extends BaseSwipeBackCompatActivity {
    private Button login_tel_btn, login_to_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login);
        StatusBarUtil.setStatusBarColor(this, R.color.colorfefefe);
        StatusBarUtil.StatusBarLightMode(ChooseLoginActivity.this);
        StatusBarUtil.StatusBarLightMode(ChooseLoginActivity.this);
        StatusBarUtil.StatusBarLightMode(ChooseLoginActivity.this,3);
        login_tel_btn = (Button) findViewById(R.id.login_tel_btn);
        login_to_register = (Button) findViewById(R.id.login_to_register);
        login_tel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
        login_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();

            }
        });
    }
}
