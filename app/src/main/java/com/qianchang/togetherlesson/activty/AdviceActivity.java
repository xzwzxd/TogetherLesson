package com.qianchang.togetherlesson.activty;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.youth.xframe.widget.XToast;

import org.apache.http.Header;
import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/21.
 */

public class AdviceActivity extends BaseSwipeBackCompatActivity {

    @Bind(R.id.ed_consultant)
    EditText edConsultant;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        ButterKnife.bind(this);
        setTitle("意见反馈");
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (edConsultant.getText().toString().trim().length()<1){

            XToast.warning("请输入内容");
        }else{
            getErcode();
        }

    }

    private void getErcode() {

        MyResponseHandler handler = new MyResponseHandler(this, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    if (jsonObject.optString("message").equals("1")){
                        XToast.success("反馈成功");
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.addsuggest(handler, MyApplication.P2PPreferences.getUid(),edConsultant.getText().toString().trim());
    }
}
