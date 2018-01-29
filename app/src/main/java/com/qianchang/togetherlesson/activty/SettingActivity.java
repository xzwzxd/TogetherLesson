package com.qianchang.togetherlesson.activty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.AppManager;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.youth.xframe.widget.XToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/21.
 */

public class SettingActivity extends BaseSwipeBackCompatActivity {

    @Bind(R.id.ly_advice)
    LinearLayout lyAdvice;
    @Bind(R.id.ly_about_us)
    LinearLayout lyAboutUs;
    @Bind(R.id.ly_update_pass)
    LinearLayout lyUpdatePass;
    @Bind(R.id.btn_out_app)
    Button btnOutApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        setTitle("设置");
        if (MyApplication.P2PPreferences.getUid().equals("")){
            btnOutApp.setVisibility(View.GONE);
        }else{
            btnOutApp.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.ly_advice, R.id.ly_about_us, R.id.ly_update_pass,R.id.btn_out_app})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_advice:
                if (MyApplication.P2PPreferences.getUid().equals("")){
                    XToast.warning("请登录");
                }else{
                    startActivity(new Intent(cnt, AdviceActivity.class));
                }
                break;
            case R.id.ly_about_us:
                startActivity(new Intent(cnt, AboutUsActivity.class));
                break;
            case R.id.ly_update_pass:
                if (MyApplication.P2PPreferences.getUid().equals("")){
                    XToast.warning("请登录");
                }else{
                    startActivity(new Intent(cnt, UpdatePassActivity.class));
                }
                break;

            case R.id.btn_out_app:
                setExit();
                break;
            default:
                break;

        }
    }

    /****
     * 注销账户
     */
    private void setExit() {
        AlertDialog.Builder ab = new AlertDialog.Builder(SettingActivity.this);
        ab.setTitle("确认退出账户吗？");
        ab.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        ab.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        MyApplication.P2PPreferences.setPassword("");
                        MyApplication.P2PPreferences.setUid("");
                        AppManager.getAppManager().finishAllActivity();
                    }
                });
        ab.create().show();
    }

}
