package com.qianchang.togetherlesson.activty;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianchang.togetherlesson.MainActivity;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.qianchang.togetherlesson.utils.AppTool;
import com.qianchang.togetherlesson.wxapi.WxShareAndLoginUtils;
import com.youth.xframe.widget.XToast;

import org.apache.http.Header;
import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/2/23.
 */

public class LoginActivity extends BaseSwipeBackCompatActivity {

    @Bind(R.id.phone)
    AutoCompleteTextView phone;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.email_login_form)
    LinearLayout emailLoginForm;
    @Bind(R.id.login_wechat)
    ImageView loginWechat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!MyApplication.P2PPreferences.getUid().equals("")) {
            startActivity(new Intent(cnt, MainActivity.class));
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setTitle("登陆");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppTool.isPhoneNum(phone)) {
                    if (AppTool.inNotNull(password, "密码")) {
                        getLogin();
                    }
                }
            }
        });

        TextView textView = (TextView) findViewById(R.id.tv_right);
        textView.setText("忘记密码");
        textView.setTextColor(Color.WHITE);
        textView.setVisibility(View.VISIBLE);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(cnt, ForgetPassActivity.class));
            }
        });


        loginWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WxShareAndLoginUtils.

                        WxLogin();
                finish();
            }
        });

        password.addTextChangedListener(mTextWatcher);
    }

    TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int arg1, int arg2,int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2,int arg3) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (password.getText().length() > 6) {
                btnLogin.setBackgroundResource(R.drawable.register_press);
            }else{
                btnLogin.setBackgroundResource(R.drawable.register_unpress);
            }
        }
    };

    /***
     * 登录接口
     */
    private void getLogin() {

        MyResponseHandler handler = new MyResponseHandler(this, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);

                    if (jsonObject.opt("message").equals("1")) {
                        MyApplication.P2PPreferences.setUid(jsonObject.optString("uid"));
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        finish();
                    } else if (jsonObject.opt("message").equals("2")) {
                        XToast.error("账号或密码错误");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.getLogin(handler, phone.getText().toString().trim(), password.getText().toString().trim());
    }


}
