package com.qianchang.togetherlesson.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


public class UIUtils {

    /**
     * 设置右侧小图标。
     *
     * @param txtView
     * @param resId
     */
    public static void setDrawableRight(Context context, TextView txtView, int resId) {
        Drawable drawable = context.getResources().getDrawable(resId);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        txtView.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 设置左侧小图标。
     *
     * @param txtView
     * @param resId
     */
    public static void setDrawableLeft(Context context, TextView txtView, int resId) {
        Drawable drawable = context.getResources().getDrawable(resId);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        txtView.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 显示Toast提示。
     *
     * @param context
     * @param msg
     */
    public static void showToast(Context context, int resId) {

        String msg = context.getResources().getString(resId);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toast提示。
     *
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * volleyError时显示的toast
     *
     * @param context
     * @param msg
     */
    public static void showErrorToast(Context context, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 设置图片的吐司提示
     *
     * @param context
     * @param view
     */
    public static void showImageToast(Context context, View view) {
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }


    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得屏幕Dpi
     *
     * @param context
     * @return
     */
    public static int getScreenDpi(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.densityDpi;
    }


}
