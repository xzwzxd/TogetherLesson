/*
 * Copyright (c) 2016 by xiaoxue. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.qianchang.togetherlesson.app;

import android.content.Context;

import com.youth.xframe.base.XApplication;


/**
 * Created by Administrator on 2016/2/23.
 */
public class MyApplication extends XApplication {
    public static P2PPreferences P2PPreferences;
    public static Context mContext;

    //实例化
    public static MyApplication sInstance;

    public static Context getContext() {
        return mContext;
    }

    public static String USER_SCHOOL = ""; //大学
    public static String USER_CLASS = ""; //年级
    public static String user_icon = "";
    public static String SCHOOL_ID = "";  //学校id

    public static String EDIT_NAME = "";  //修改姓名
    public static  String SEX="";//性別

    public static  String IS_SCHOOL_VISBLE="";// 学校列表是否显示

    @Override
    public void onCreate() {
        super.onCreate();
        if (mContext == null) mContext = this;
        P2PPreferences = new P2PPreferences(this);
        mContext = getApplicationContext();
        sInstance = this;


    }

}
