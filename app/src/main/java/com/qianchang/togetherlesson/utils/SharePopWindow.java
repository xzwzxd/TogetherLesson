package com.qianchang.togetherlesson.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.qianchang.togetherlesson.R;


/**
 * Created by tx on 2016/6/23.
 *
 */
public class SharePopWindow extends PopupWindow {

    private View mView;
    private View.OnClickListener mItemClickListener;
    String string="0";
    @SuppressLint("InflateParams")
    public SharePopWindow(Context context, View.OnClickListener itemClickListener,String string) {
        mView = LayoutInflater.from(context).inflate(R.layout.pop_share,null);
        mItemClickListener = itemClickListener;
        this.setContentView(mView);

        int width = ScreenUtils.getScreenWidth(context);
        int height = ScreenUtils.getScreenHeight(context);
        this.setHeight(height);
        this.setWidth(width);
        this.string=string;
        initView();
    }

    private void initView() {
        Button cancel = (Button) mView.findViewById(R.id.cancel);
        RelativeLayout can_be_covered_parent = (RelativeLayout)mView.findViewById(R.id.can_be_covered_parent);
        cancel.setOnClickListener(dismissListener);
        can_be_covered_parent.setOnClickListener(dismissListener);
        LinearLayout wechat = (LinearLayout) mView.findViewById(R.id.wechat);
        LinearLayout circle = (LinearLayout)mView.findViewById(R.id.circle);
        LinearLayout to_report = (LinearLayout)mView.findViewById(R.id.to_report);
        wechat.setOnClickListener(mItemClickListener);
        circle.setOnClickListener(mItemClickListener);
        to_report.setOnClickListener(mItemClickListener);
        if (string.equals("1")){
            to_report.setVisibility(View.GONE);
        }else{
            to_report.setVisibility(View.VISIBLE);

        }
    }



   private View.OnClickListener dismissListener = new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           dismiss();
       }
   };


}
