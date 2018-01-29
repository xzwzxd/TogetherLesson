package com.youth.xframe.app;

import android.content.Context;


/**
 * Created by zcx on 2015/5/26.
 */
public class XPreferences extends AppPreferences {

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

    public final String COMMUITY_NAME = "commuity_name"; //地址名字



    public XPreferences(Context context) {

        super(context, "p2p");

    }

    public String getFirst_time() {
        return First_time;
    }

    public void setFirst_time(String first_time) {
        First_time = first_time;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getUID() {
        return UID;
    }

    public String getIS_FIRST() {
        return IS_FIRST;
    }

    public String getIS_LOGIN() {
        return IS_LOGIN;
    }

    public String getIS_AUTO_LOGIN() {
        return IS_AUTO_LOGIN;
    }

    public String getUSER_TEL() {
        return USER_TEL;
    }

    public String getUSER_PIC() {
        return USER_PIC;
    }

    public String getALL_MONEY() {
        return ALL_MONEY;
    }

    public String getAddress_phone() {
        return address_phone;
    }

    public String getADDRESS_DETIAL() {
        return ADDRESS_DETIAL;
    }

    public String getCOMMUITY_NAME() {
        return COMMUITY_NAME;
    }
}
