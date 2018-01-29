package com.qianchang.togetherlesson.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.qianchang.togetherlesson.MainActivity;
import com.qianchang.togetherlesson.app.AppManager;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseActivity;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.qianchang.togetherlesson.utils.GetToast;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    public  int WX_LOGIN = 1;

    private IWXAPI iwxapi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        iwxapi = WXAPIFactory.createWXAPI(this, Config.APP_ID, false);
        //接收到分享以及登录的intent传递handleIntent方法，处理结果
        iwxapi.handleIntent(getIntent(), this);

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    //请求回调结果处理
    @Override
    public void onResp(BaseResp baseResp) {
        //微信登录为getType为1，分享为0
        if (baseResp.getType() == WX_LOGIN){
            //登录回调
            SendAuth.Resp resp = (SendAuth.Resp) baseResp;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String code = String.valueOf(resp.code);
                    Log.d("code","++++++++++++++++++++++++++"+code);
//                    获取用户信息
                    getAccessToken(code);
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                    break;
                default:
                    break;
            }
        }else{
            //分享成功回调
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    //分享成功
                    Toast.makeText(WXEntryActivity.this, "分享成功", Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    //分享取消
                    Toast.makeText(WXEntryActivity.this, "分享取消", Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    //分享拒绝
                    Toast.makeText(WXEntryActivity.this, "分享拒绝", Toast.LENGTH_LONG).show();
                    break;
            }
        }
        finish();
    }

    private void getAccessToken(String code) {
        //获取授权

//                      https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
        String http = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Config.APP_ID + "&secret=" + Config.APP_SERECET.trim() + "&code=" + code + "&grant_type=authorization_code";
        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("response","++++++++++++++++++++++"+response);
                String access = null;
                String openId = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    access = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MyResponseHandler handler = new MyResponseHandler(getApplicationContext(), true) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        super.onSuccess(statusCode, headers, responseString);
                        try {
                            JSONObjectEx jsonObject = new JSONObjectEx(responseString);

                            if ( jsonObject.opt("message").equals("1")){

                                JSONObjectEx jsonObject1 = new JSONObjectEx(jsonObject.optString("data"));
                                jsonObject1.optString("id");
                                jsonObject1.optString("mobile");
                                jsonObject1.optString("username");
                                jsonObject1.optString("avatar");
                                MyApplication.P2PPreferences.setUid(  jsonObject1.optString("id"));
                                startActivity(new Intent(WXEntryActivity.this,MainActivity.class));
                                AppManager.getAppManager().finishAllActivity();
                                Log.d("mobile",jsonObject1.optString("mobile"));
                                Log.d("username",jsonObject1.optString("username"));
                                Log.d("avatar",jsonObject1.optString("avatar"));
                                finish();
                            }else   if ( jsonObject.opt("message").equals("2")){
                                GetToast.useString(WXEntryActivity.this,"账号或密码错误");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ServerData.login_wechat(handler,openId,access);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(WXEntryActivity.this, "登录失败2", Toast.LENGTH_SHORT).show();
            }
        };
        OkHttpUtils.get(http, resultCallback);
    }
}

