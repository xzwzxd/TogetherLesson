package com.qianchang.togetherlesson.activty;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.qianchang.togetherlesson.utils.AppTool;
import com.youth.xframe.widget.XToast;

import org.apache.http.Header;
import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by admin on 2017/2/24.
 */

public class RegisterActivity extends BaseSwipeBackCompatActivity {

    @Bind(R.id.email)
    AutoCompleteTextView email;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.get_register_sms_btn)
    TextView getRegisterSmsBtn;
    @Bind(R.id.register_user_pass)
    EditText registerUserPass;
    @Bind(R.id.call_num)
    EditText callNum;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.email_login_form)
    LinearLayout emailLoginForm;
    @Bind(R.id.login_form)
    ScrollView loginForm;
    @Bind(R.id.login_progress)
    ProgressBar loginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setTitle("注册");
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (email.getText().toString().trim().equals("")) {
                    getRegisterSmsBtn.setBackgroundResource(R.mipmap.register_sms);
                    getRegisterSmsBtn.setTextColor(Color.BLACK);

                } else {
                    getRegisterSmsBtn.setBackgroundResource(R.mipmap.register_sms_select);
                    getRegisterSmsBtn.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(mTextWatcher);
        registerUserPass.addTextChangedListener(mTextWatcher);
    }

    TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (password.getText().length() > 0 & registerUserPass.getText().length() > 0) {
                btnRegister.setBackgroundResource(R.drawable.register_press);
                btnRegister.setTextColor(getResources().getColor(R.color.white));
            } else {
                btnRegister.setBackgroundResource(R.drawable.register_unpress);
                btnRegister.setTextColor(getResources().getColor(R.color.grey));
            }
        }
    };

    /****
     * 获取短信
     */
    private void getMsmRegister() {

        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObjectEx je = new JSONObjectEx(responseString);
                    if (je.optString("message").equals("1")) {
//                        XToast.success("再按一次关闭软件");

                        timer.start();
                    } else {
                        XToast.warning("请输入验证码");

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        ServerData.re_sms(handler, email.getText().toString().trim());
    }


    /****
     * 注册借口
     */
    private void getRegister() {

        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObjectEx je = new JSONObjectEx(responseString);
                    if (je.opt("message").equals("1")) {
                        XToast.success(je.optString("alert"));
                        MyApplication.P2PPreferences.setUid(je.optString("uid"));
                        startActivity(new Intent(cnt, EditorPersonInfoActivity.class));
                        finish();
                    } else {
                        XToast.error(je.optString("alert"));
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        ServerData.getregister(handler,
                email.getText().toString().trim(),
                registerUserPass.getText().toString().trim(),
                callNum.getText().toString().trim()
        );
    }

    /*****
     * 计时器
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            getRegisterSmsBtn.setText((millisUntilFinished / 1000) + "s");
            getRegisterSmsBtn.setEnabled(false);
        }

        @Override
        public void onFinish() {
            getRegisterSmsBtn.setEnabled(true);
            getRegisterSmsBtn.setText("重新获取");
        }
    };

    private boolean isCommit() {
        if (TextUtils.isEmpty(email.getText().toString())) {
            XToast.warning("请填写手机号");
            return false;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            XToast.warning("请填写验证码");

            return false;
        }
        if (TextUtils.isEmpty(registerUserPass.getText().toString())) {
            XToast.warning("请填写密码");

            return false;
        }

        return true;
    }

    @OnClick({R.id.get_register_sms_btn, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_register_sms_btn:


                if (AppTool.isPhoneNum(email)) {
                    getMsmRegister();
                }
                break;
            case R.id.btn_register:
                if (!isCommit()) {
                    return;
                }
                getRegister();
                break;
        }
    }
}
