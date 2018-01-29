package com.qianchang.togetherlesson.utils.dialog;

import android.app.Dialog;
import android.content.Context;


/**
 * Created by Administrator on 2015/6/11.
 */
public class P2PDialog extends Dialog {

    public P2PDialog(Context context, int theme){

        super(context, theme);

    }

    @Override
    public void setContentView(int layoutResID) {

        super.setContentView(layoutResID);

//        AppContext.ScaleScreenHelper.loadView((ViewGroup) getWindow().getDecorView());

    }
}
