package com.qianchang.togetherlesson.app;

import android.content.Context;


/**
 * Created by zcx on 2015/5/26.
 */
public class P2PPreferences extends AppPreferences {

    public String First_time = "First_time"; //首次进入导航页

    public final String USERNAME = "username";// 账户

    public final String PASSWORD = "password";// 密码

    public final String UID = "uid";// 服务器返回uid

    public final String IS_FIRST = "is_first";// 是否第一次打开

    public final String IS_LOGIN = "is_login";// 是否登陆

    public final String IS_AUTO_LOGIN = "is_auto";// 是否登陆

    public final String USER_TEL = "user_tel";// 电话

    public final String USER_PIC = "user_pic";// 电话

    public final String ALL_MONEY = "all_money";//账号余额

    public final String address_phone = "ADDRESS_phone"; //地址名字

    public final String ADDRESS_DETIAL = "ADDRESS_DETIAL"; //地址名字
    public final String COMMUITY_NAME = "COMMUITY_NAME"; //地址名字


    public P2PPreferences(Context context) {

        super(context, "p2p");

    }

    public void setuser_pic(String user_pic) {

        putString(USER_PIC, user_pic);

    }

    public String getuser_picL() {

        return getString(USER_PIC, "");

    }

    public void setUSER_TEL(String user_tel) {

        putString(USER_TEL, user_tel);

    }

    public String getUSER_TEL() {

        return getString(USER_TEL, "");

    }

    public void setUid(String uid) {

        putString(UID, uid);

    }

    public String getUid() {

        return getString(UID, "");

    }

    public void setUserName(String userName) {

        putString(USERNAME, userName);

    }

    public String getUserName() {

        return getString(USERNAME, "");

    }

    public void setPassword(String password) {

        putString(PASSWORD, password);

    }

    public String getPassword() {

        return getString(PASSWORD, "");

    }

    public void setIsFirst(boolean isFirst) {

        putBoolean(IS_FIRST, isFirst);

    }

    public boolean getIsFirst() {

        return getBoolean(IS_FIRST, true);

    }

    public void setIsLogin(boolean isLogin) {

        putBoolean(IS_LOGIN, isLogin);

    }

    public boolean getIsLogin() {

        return getBoolean(IS_LOGIN, false);

    }

    public void setIsAuto(boolean isAuto) {

        putBoolean(IS_AUTO_LOGIN, isAuto);

    }

    public boolean getIsAuto() {

        return getBoolean(IS_AUTO_LOGIN, false);

    }


    public void setALL_MONEY(String all_money) {

        putString(ALL_MONEY, all_money);

    }

    public String getall_money() {

        return getString(ALL_MONEY, "");

    }


    public void setaddress_phone(String ADDRESS_PHONE) {

        putString(address_phone, ADDRESS_PHONE);

    }

    public String getaddress_phoneY() {

        return getString(address_phone, "");

    }


    public void setADDRESS_DETIAL(String address_detial) {

        putString(ADDRESS_DETIAL, address_detial);

    }

    public String getADDRESS_DETIAL() {

        return getString(ADDRESS_DETIAL, "");

    }



    public void setCommuity_name(String commuity_name) {

        putString(COMMUITY_NAME, commuity_name);

    }

    public String getCommuity_name() {

        return getString(COMMUITY_NAME, "");

    }
}
