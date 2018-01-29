package com.qianchang.togetherlesson.activty;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.baseAdapter.QuestionAdapter;
import com.qianchang.togetherlesson.bean.QuestionlistBean;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.qianchang.togetherlesson.utils.GetToast;
import com.qianchang.togetherlesson.utils.MyListView;
import com.youth.xframe.widget.XToast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 课程咨询
 * Created by Administrator on 2017/4/24.
 */

public class ConsultantActivty extends BaseSwipeBackCompatActivity {

    @Bind(R.id.ed_consultant)
    EditText edConsultant;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    @Bind(R.id.lv_chat)
    MyListView lvChat;
    @Bind(R.id.ly_softP)
    LinearLayout lySoftP;
    private List<QuestionlistBean> questionlistBeanList = new ArrayList<>();
    private QuestionAdapter questionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_zixun);
        ButterKnife.bind(this);
        questionAdapter = new QuestionAdapter(cnt, questionlistBeanList);
        lvChat.setAdapter(questionAdapter);
        getDate();
    }

    private void getDate() {
        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                questionlistBeanList.clear();
                Logger.json(responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    if (jsonObject.optString("message").equals("1")) {

                        JSONArray bannerjsonArray = jsonObject.optJSONArray("questionlist");
                        for (int i = 0; i < bannerjsonArray.length(); i++) {
                            JSONObject object = bannerjsonArray.optJSONObject(i);
                            QuestionlistBean courseBean = new QuestionlistBean();
                            courseBean.id = object.optString("id");
                            courseBean.title = object.optString("title");
                            courseBean.content = object.optString("content");
                            questionlistBeanList.add(courseBean);
                        }
                        questionAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.intoconsult(handler, MyApplication.SCHOOL_ID);
    }

    @OnClick({R.id.ly_softP, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_softP:
                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(ConsultantActivty.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                break;
            case R.id.btn_submit:

                if (!TextUtils.isEmpty(edConsultant.getText().toString())){
                    postAnd();
                }else{
                    XToast.warning("输入内容");

                }
                break;
        }
    }

    private void postAnd() {
        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                super.onSuccess(statusCode, headers, responseString);
                Logger.json(responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    if (jsonObject.optString("message").equals("1")) {
                        XToast.success("咨询成功");
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.addconsult(handler, MyApplication.SCHOOL_ID,MyApplication.P2PPreferences.getUid());
    }

}
