package com.youth.xframe.base;

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

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.youth.xframe.R;
import com.youth.xframe.common.XActivityStack;


public abstract class XActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{

    public Context cnt;// 指向当前的activity
    private TextView head_title;
    public TextView head_right;
    private ImageView head_left,img_head_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        XActivityStack.getInstance().addActivity(this);
        cnt = this;
//		StatusBarUtil.setStatusBarColor(this, R.color.colorWindowBg);
//		StatusBarUtil.StatusBarLightMode(BaseActivity.this);
//		StatusBarUtil.StatusBarLightMode(BaseActivity.this);
//		StatusBarUtil.StatusBarLightMode(BaseActivity.this,3);

    }
    private void loadTitleView() {
        head_title = (TextView) findViewById(R.id.tv_title_name);
        head_left = (ImageView) findViewById(R.id.img_title_back);
        img_head_right = (ImageView) findViewById(R.id.img_head_right);
        head_right = (TextView) findViewById(R.id.head_right);
        head_right.setOnClickListener(this);

        head_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
     * 设置左上角按钮背景图片
     *
     * @param resid
     */
    public void setLeftButtonBgResId1(int resid) {
        head_left.setBackgroundResource(resid);
        head_left.setVisibility(View.VISIBLE);

    }
    /**
     * 设置右上角按钮背景图片
     *
     * @param resid
     */
    public void setRightButtonBgResId1(int resid) {
        img_head_right.setBackgroundResource(resid);
        img_head_right.setVisibility(View.VISIBLE);
        img_head_right.setVisibility(View.VISIBLE);
    }


    /**
     * 设置左上角按钮是否显示
     *
     * @param visibility
     */
    public void setLeftButtonVisibility(int visibility) {
        head_left.setVisibility(visibility);
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
        XActivityStack.getInstance().finishActivity(this);
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

    /**
     * use SytemBarTintManager
     *
     * @param tintDrawable
     */
    public void setSystemBarTintDrawable(Drawable tintDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            if (tintDrawable != null) {
                mTintManager.setStatusBarTintEnabled(true);
                mTintManager.setTintDrawable(tintDrawable);
            } else {
                mTintManager.setStatusBarTintEnabled(false);
                mTintManager.setTintDrawable(null);
            }
        }
    }
}
