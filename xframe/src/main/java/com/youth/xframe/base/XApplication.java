package com.youth.xframe.base;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.youth.xframe.XFrame;
import com.youth.xframe.app.XPreferences;


public class XApplication extends MultiDexApplication {
    private static XApplication instance;
    public  static XPreferences xPreferences;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        xPreferences=new XPreferences(this);
        XFrame.init(this);
        Fresco.initialize(this);
    }


    public static XApplication getInstance() {
        return instance;
    }


}
