package com.qianchang.togetherlesson.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.utils.UIUtils;

/**
 * @author yao.feng
 *         <p>
 *         2015-8-5
 */
public class CustomProgressDialog extends Dialog {

    private Context mContext;
    //	private ImageView mImageView;
//	private Animation operatingAnim;
    private String msg;
    private TextView msgText;
    private boolean isCancelable = true;

    /**
     * @param context
     */
    public CustomProgressDialog(Context context) {
        super(context, R.style.progress_loading_dialog);
        this.mContext = context;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progressbar);
        msgText = (TextView) findViewById(R.id.progress_msg);

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (UIUtils.getScreenHeight(mContext) * 0.15);
        p.width = (int) (UIUtils.getScreenWidth(mContext) * 0.3);
        // p.y = (int) (d.getHeight() * 0.3);
        dialogWindow.setAttributes(p);

    }

    @Override
    public void show() {
        super.show();
        //mWindow.setBackgroundDrawableResource(R.color.transparentcolor);
        //mImageView.startAnimation(operatingAnim);
        if (!isCancelable) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    if (isShowing()) {
                        dismiss();
                    }
                }
            }, 35000);
        }
        if (!TextUtils.isEmpty(msg)) {
            msgText.setVisibility(View.VISIBLE);
            msgText.setText(msg);
        } else {
            msgText.setVisibility(View.GONE);
        }
    }

    @Override
    public void dismiss() {
        Window mWindow = getWindow();
        if (mWindow == null) {
            return;
        }
        if (mWindow.getDecorView() == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!mWindow.getDecorView().isAttachedToWindow()) {
                return;
            }
        }
        super.dismiss();
        //operatingAnim.cancel();
    }

    @Override
    public void setCancelable(boolean flag) {
        isCancelable = flag;
        super.setCancelable(flag);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus) {
            dismiss();
        }
    }

}
