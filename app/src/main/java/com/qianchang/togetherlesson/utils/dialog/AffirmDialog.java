package com.qianchang.togetherlesson.utils.dialog;

import android.content.Context;
import android.widget.TextView;

import com.qianchang.togetherlesson.R;


/**
 * Created by Administrator on 2015/6/10.
 */
public abstract class AffirmDialog extends AppDialog {

    private TextView text;

    public AffirmDialog(Context context, String string, int theme) {

        super(context,theme);

        addContent(R.layout.dialog_content_alert);

        text = (TextView)findViewById(R.id.alert_text);

        text.setText(string);

        addButton(new Button("确定") {

            @Override
            public void onClick() {

                onYesClick();

                dismiss();
            }
        }, new Button("取消") {
            @Override
            public void onClick() {
                dismiss();
            }

        });

    }

    protected abstract void onYesClick();

}
