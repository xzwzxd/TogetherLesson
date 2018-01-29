package com.qianchang.togetherlesson.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.activty.AdviceActivity;
import com.qianchang.togetherlesson.activty.ChooseLoginActivity;
import com.qianchang.togetherlesson.activty.CollectionActivity;
import com.qianchang.togetherlesson.activty.MessageActivity;
import com.qianchang.togetherlesson.activty.MyComposeActivity;
import com.qianchang.togetherlesson.activty.PersonalInfoActivity;
import com.qianchang.togetherlesson.activty.SettingActivity;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.AttachFragment;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.api.UrlApi;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.qianchang.togetherlesson.utils.GlideCircleTransform;
import com.youth.xframe.widget.XToast;
import com.zhy.autolayout.AutoLinearLayout;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2016/10/9.
 */
public class MineFragment extends AttachFragment implements View.OnClickListener {

    View view;
    @Bind(R.id.set)
    AutoLinearLayout set;
    @Bind(R.id.user_icon)
    ImageView userIcon;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.user_num)
    TextView userNum;
    private RequestManager glideRequest;

    public OnR onR;

    private AutoLinearLayout mLinearNews;
    private AutoLinearLayout mLinearCillect, mLinearCoupon, mLinearAdvice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        initViewPagerAndTabs();
        ButterKnife.bind(this, view);

        if (!MyApplication.P2PPreferences.getUid().equals("")) {
            getLogin();
        }
        onR = new OnR() {
            @Override
            public void onR() {
                if (!MyApplication.P2PPreferences.getUid().equals("")) {
                    getLogin();
                }
            }
        };
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mLinearNews:

                if (MyApplication.P2PPreferences.getUid().equals("")){
                    XToast.warning("请登录");
                }else{
                    startActivity(new Intent(getActivity(), MessageActivity.class));
                }
                break;
            case R.id.mLinearCillect:

                if (MyApplication.P2PPreferences.getUid().equals("")){
                    XToast.warning("请登录");
                }else{
                    startActivity(new Intent(getActivity(), CollectionActivity.class));
                }
                break;
            case R.id.mLinearCoupon:

                if (MyApplication.P2PPreferences.getUid().equals("")){
                    XToast.warning("请登录");
                }else{
                    startActivity(new Intent(getActivity(), MyComposeActivity.class));
                }
                break;
            case R.id.mLinearAdvice:

                if (MyApplication.P2PPreferences.getUid().equals("")){
                    XToast.warning("请登录");
                }else{
                    startActivity(new Intent(getActivity(), AdviceActivity.class));
                }

                break;
            default:
                break;
        }

    }

    public interface OnR {
        public void onR();
    }

    private void initViewPagerAndTabs() {


        mLinearNews = (AutoLinearLayout) view.findViewById(R.id.mLinearNews);
        mLinearNews.setOnClickListener(this);

        mLinearCillect = (AutoLinearLayout) view.findViewById(R.id.mLinearCillect);
        mLinearCillect.setOnClickListener(this);

        mLinearCoupon = (AutoLinearLayout) view.findViewById(R.id.mLinearCoupon);
        mLinearCoupon.setOnClickListener(this);

        mLinearAdvice = (AutoLinearLayout) view.findViewById(R.id.mLinearAdvice);
        mLinearAdvice.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.set, R.id.user_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.set:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.user_icon:
                if (MyApplication.P2PPreferences.getUid().equals("")) {
                    startActivity(new Intent(getActivity(), ChooseLoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), PersonalInfoActivity.class));
                }

                break;
        }
    }

    @Override
    public void onResume() {
        try {
            getLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    private void getLogin() {

        MyResponseHandler handler = new MyResponseHandler(getActivity(), true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    Logger.json(responseString);
                    if (jsonObject.opt("message").equals("1")) {

                        JSONObject jsonObject1 = new JSONObject(jsonObject.optString("usermessage"));
                        userName.setText(jsonObject1.optString("username"));
                        MyApplication.EDIT_NAME = jsonObject1.optString("username");
                        MyApplication.USER_SCHOOL = jsonObject1.optString("school");
                        MyApplication.USER_CLASS = jsonObject1.optString("class");
                        userNum.setText("聚课学号:" + jsonObject1.optString("code"));
                        glideRequest = Glide.with(getActivity());
                        glideRequest.load(UrlApi.ip_pic + jsonObject1.optString("avatar")).transform(new GlideCircleTransform(getActivity())).placeholder(R.mipmap.user_icon_defalt).into(userIcon);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.usermessage(handler, MyApplication.P2PPreferences.getUid());
    }
}
