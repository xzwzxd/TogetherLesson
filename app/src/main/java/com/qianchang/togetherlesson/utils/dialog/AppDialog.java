package com.qianchang.togetherlesson.utils.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianchang.togetherlesson.R;


/**
 * Created by Administrator on 2015/6/2.
 */
public class AppDialog extends P2PDialog {

    private LayoutInflater layoutInflater;

    private LinearLayout contentLayout, buttonLayout;

    public AppDialog(Context context, int theme) {

        super(context,theme);

        setContentView(R.layout.dialog_app);

        layoutInflater = getLayoutInflater();

        contentLayout = (LinearLayout)findViewById(R.id.dialog_app_content);

        buttonLayout = (LinearLayout)findViewById(R.id.dialog_app_button);

    }

    protected void addContent(int layoutResId){

        contentLayout.addView(layoutInflater.inflate(layoutResId, null));

    }

    protected void addButton(Button...buttons){

        for (int i = 0 ; i < buttons.length ; i ++){

            final Button button = buttons[i];

            TextView textView = new TextView(getContext());

            textView.setTag("31");

            textView.setTextColor(Color.parseColor("#3195e3"));

            textView.setText(button.name);

            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

            textView.setGravity(Gravity.CENTER);

            textView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    button.onClick();

                }

            });

            buttonLayout.addView(textView);

            if(i + 1 < buttons.length){

                View view = new View(getContext());

                view.setBackgroundColor(Color.parseColor("#dddddd"));

                view.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.WRAP_CONTENT));

                buttonLayout.addView(view);

            }

        }

    }

    public abstract class Button{

        public String name;

        public Button(String name){

            this.name = name;

        }

        public abstract void onClick();

    }

}
