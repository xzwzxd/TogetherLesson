package com.qianchang.togetherlesson.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.qianchang.togetherlesson.R;


/**
 * Created by zhaowei on 2016/5/7.
 */
public class SelectSexWindow extends PopupWindow {

    private Button select_man, select_women, select_cancel;

    private View mMenuView;

    public SelectSexWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_layout_lesson, null);
        select_man = (Button) mMenuView.findViewById(R.id.select_man);
        select_women = (Button) mMenuView.findViewById(R.id.select_women);

        select_cancel = (Button) mMenuView.findViewById(R.id.select_cancel);

        select_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dismiss();
            }
        });

        select_man.setOnClickListener(itemsOnClick);
        select_women.setOnClickListener(itemsOnClick);

        this.setContentView(mMenuView);

        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);

        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        this.setFocusable(true);

        ColorDrawable dw = new ColorDrawable(0xb0000000);

        this.setBackgroundDrawable(dw);

        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.lesson_pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}
