package com.qianchang.togetherlesson.activty;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.qianchang.togetherlesson.utils.GetToast;

import org.apache.http.Header;
import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HT on 2017/8/10.
 */

public class UpdatePassActivity extends BaseSwipeBackCompatActivity {

    @Bind(R.id.update_old_pass)
    AutoCompleteTextView updateOldPass;
    @Bind(R.id.update_new_pass)
    AutoCompleteTextView updateNewPass;
    @Bind(R.id.update_new_pass_agin)
    EditText updateNewPassAgin;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.email_login_form)
    LinearLayout emailLoginForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);
        ButterKnife.bind(this);
        setTitle("修改密码");

    }

    private boolean isCommit() {
        if (TextUtils.isEmpty(updateOldPass.getText().toString())) {
            GetToast.useString(cnt, "请填写原密码");
            return false;
        }
        if (TextUtils.isEmpty(updateNewPass.getText().toString())) {
            GetToast.useString(cnt, "请填写新密码");
            return false;
        }
        if (TextUtils.isEmpty(updateNewPassAgin.getText().toString())) {
            GetToast.useString(cnt, "请填写确认密码");
            return false;
        }
        if (updateOldPass.getText().toString().equals(TextUtils.isEmpty(updateNewPass.getText().toString()))) {
            GetToast.useString(cnt, "新旧密码不能一致");
            return false;
        }
        if (updateNewPass.getText().toString().length() <= 5 || updateNewPass.getText().toString().length() > 37) {
            GetToast.useString(cnt, "请输入密码6到32位数字、字母以及下划线_");
            return false;
        }
        return true;
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {

        if (!isCommit()) {
            if (!updateNewPass.getText().toString().equals(TextUtils.isEmpty(updateNewPassAgin.getText().toString()))){
                GetToast.useString(cnt, "请输出相同的新密码");
            }
            return;
        }
        try {
            getLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     *  通过原密码。修改密码
     */
    private void getLogin() {

        MyResponseHandler handler = new MyResponseHandler(this, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);

                    if (jsonObject.opt("message").equals("1")) {
                        GetToast.useString(cnt,"修改成功");
                        finish();

                    } else if (jsonObject.opt("message").equals("2")) {
                        GetToast.useString(cnt,"修改失败，请检查账号和密码");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.resetpassword(handler, MyApplication.P2PPreferences.getUid(), updateNewPass.getText().toString().trim(), updateOldPass.getText().toString().trim());
    }

}
