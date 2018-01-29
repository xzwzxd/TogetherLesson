package com.qianchang.togetherlesson.activty;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.utils.AppTool;
import com.qianchang.togetherlesson.utils.GetToast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by admin on 2016/10/9.
 */
public class ForgetPassActivity extends BaseSwipeBackCompatActivity {


    @Bind(R.id.forget_user_phone)
    EditText forgetUserPhone;
    @Bind(R.id.forget_yanzheng_pass)
    EditText forgetYanzhengPass;
    @Bind(R.id.get_register_btn)
    Button getRegisterBtn;
    @Bind(R.id.forget_user_pass)
    EditText forgetUserPass;
    @Bind(R.id.register_user_btn)
    Button registerUserBtn;
    private String moble, okCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        ButterKnife.bind(this);
        setTitle("忘记密码");
        forgetYanzhengPass.addTextChangedListener(mTextWatcher);
        forgetUserPass.addTextChangedListener(mTextWatcher);
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
            if (forgetYanzhengPass.getText().length() > 0) {
                getRegisterBtn.setBackgroundResource(R.drawable.register_press);
                getRegisterBtn.setTextColor(getResources().getColor(R.color.white));
            }else{
                getRegisterBtn.setBackgroundResource(R.drawable.register_unpress);
                getRegisterBtn.setTextColor(getResources().getColor(R.color.grey));

            }


            if (forgetUserPass.getText().length() > 0) {
                registerUserBtn.setBackgroundResource(R.drawable.register_press);
            }else{
                registerUserBtn.setBackgroundResource(R.drawable.register_unpress);

            }
        }
    };

    @OnClick({R.id.get_register_btn, R.id.register_user_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_register_btn:

                if (AppTool.isPhoneNum(forgetUserPhone)) {
                    getMsmRegister();
                }
                break;
            case R.id.register_user_btn:

                moble = forgetUserPhone.getText().toString().trim();

                if (!isCommit()) {
                    return;
                }
                getRegister();
                finish();
                break;
            default:
                finish();
                break;
        }
    }

    private void getMsmRegister() {
        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.optString("message").equals("1")) {
                        okCode = jsonObject.optString("code");
                        Log.d("okCode", "" + okCode);
                        Toast.makeText(cnt, "验证码获取成功", Toast.LENGTH_SHORT).show();
                        timer.start();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.forget_sms(handler, forgetUserPhone.getText().toString().trim());
    }


    /*****
     *
     *  修改密码
     */
    private void getRegister() {
        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.optString("message").equals("1")) {
                        Toast.makeText(cnt, "修改成功", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.findpwdaction(handler, forgetUserPhone.getText().toString().trim(), forgetUserPass.getText().toString().trim());
    }

    /**
     * *
     * 时间
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            getRegisterBtn.setText((millisUntilFinished / 1000) + "s");
            getRegisterBtn.setEnabled(false);
        }

        @Override
        public void onFinish() {
            getRegisterBtn.setEnabled(true);
            getRegisterBtn.setText("获取验证码");
        }
    };

    private boolean isCommit() {
        if (TextUtils.isEmpty(forgetUserPhone.getText().toString())) {
            GetToast.useString(cnt, "请填写手机号");
            return false;
        }


        if (TextUtils.isEmpty(forgetYanzhengPass.getText().toString())) {
            GetToast.useString(cnt, "请填写验证码");
            return false;
        }

        if (TextUtils.isEmpty(forgetUserPass.getText().toString())) {
            GetToast.useString(cnt, "请填写密码");
            return false;
        }
        return true;
    }
}
