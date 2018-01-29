package com.qianchang.togetherlesson.utils.dialog;

import android.content.Context;

import com.qianchang.togetherlesson.R;


/**
 * Created by Administrator on 2015/6/11.
 */
public class ProgressDialog extends P2PDialog {

    public ProgressDialog(Context context, int theme) {

        super(context,theme);

        setContentView(R.layout.dialog_progress);

        setCanceledOnTouchOutside(false);

    }

}
