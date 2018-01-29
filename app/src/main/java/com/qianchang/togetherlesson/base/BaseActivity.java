package com.qianchang.togetherlesson.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.AppManager;
import com.zhy.autolayout.AutoLayoutActivity;



/**
 * Created by zhaowei on 2016/3/28.
 */
public class BaseActivity extends AutoLayoutActivity implements View.OnClickListener{

    public Context cnt;// 指向当前的activity
    private TextView head_title;
    public TextView head_right;
    private ImageView head_left,shoucang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        AppManager.getAppManager().addActivity(this);
        cnt = this;
        setTranslucentStatus(true);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.base_title);
        loadTitleView();
        LinearLayout llContent = (LinearLayout) findViewById(R.id.llContent);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(layoutResID, llContent, false);
        llContent.addView(v);

    }
    private void loadTitleView() {
        head_left = (ImageView) findViewById(R.id.img_title_back);
        head_title = (TextView) findViewById(R.id.tv_title_name);
        head_right = (TextView) findViewById(R.id.tv_right);
        shoucang= (ImageView) findViewById(R.id.shoucang);
        head_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        head_title.setText(title);
    }

    /**
     * 设置标题Title的颜色
     *
     * @param colorId
     */
    public void setTitleTextBackgroundColor(int colorId) {
        head_title.setTextColor(colorId);
    }

    /**
     * 设置右上角按钮是否显示
     *
     * @param visibility
     */
    public void setRightButtonVisibility(int visibility) {
        head_right.setVisibility(visibility);
    }
    /**
     * 设置s=shoucang按钮是否显示
     *
     * @param visibility
     */
    public void setShoucangButtonVisibility(int visibility) {
        shoucang.setVisibility(visibility);
    }

    /**
     * 设置左上角文字
     *
     * @param text
     */
    public void setRightButtonText(CharSequence text) {
        head_right.setVisibility(View.VISIBLE);
        head_right.setText(text);
    }
    /**
     * 设置左上角文字顏色
     *
     * @param resid
     */
    public void setRightButtonBgResId(int resid) {
        Drawable drawable = getResources().getDrawable(resid);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        head_right.setCompoundDrawables(null, null, drawable, null);
        head_right.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.img_title_back) {
            goBack(v);
        }
    }

    public void goBack(View v) {
        onBackPressed();
    }

    /**
     * TODO 子类重写此方法，可同时监听左上角返回键和手机物理返回键
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {

        // TODO Auto-generated method stub

        super.onResume();
    }

    @Override
    protected void onPause() {

        /* TODO Auto-generated method stub*/
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);


    }

    /**
     * set status bar translucency
     *
     * @param on
     */
    public void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

}
