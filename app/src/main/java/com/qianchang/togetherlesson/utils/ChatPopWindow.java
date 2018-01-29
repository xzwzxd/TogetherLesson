package com.qianchang.togetherlesson.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.bumptech.glide.RequestManager;
import com.qianchang.togetherlesson.R;


/**
 * Created by tx on 2016/6/23.
 *
 */
public class ChatPopWindow extends PopupWindow {

    private View mView;
    private View.OnClickListener mItemClickListener;
    private RequestManager glideRequest;
    @SuppressLint("InflateParams")
    public ChatPopWindow(Context context, View.OnClickListener itemClickListener, String  url,String  qqqun,String  school_name) {
        mView = LayoutInflater.from(context).inflate(R.layout.pop_chat,null);
        mItemClickListener = itemClickListener;
        this.setContentView(mView);
        int width = ScreenUtils.getScreenWidth(context);
        int height = ScreenUtils.getScreenHeight(context);
        this.setHeight(height);
        this.setWidth(width);


        Button cancel = (Button) mView.findViewById(R.id.cancel);
        RelativeLayout can_be_covered_parent = (RelativeLayout)mView.findViewById(R.id.can_be_covered_parent);
        cancel.setOnClickListener(dismissListener);
        can_be_covered_parent.setOnClickListener(dismissListener);
        LinearLayout ly_chat_email = (LinearLayout) mView.findViewById(R.id.ly_chat_email);

        LinearLayout ly_chat_to_person = (LinearLayout)mView.findViewById(R.id.ly_chat_to_person);
        LinearLayout ly_chat_to_group = (LinearLayout)mView.findViewById(R.id.ly_chat_to_group);
//        ImageView img_school_icon= (ImageView) mView.findViewById(R.id.img_school_icon);
//        TextView  tv_school_qqqun= (TextView) mView.findViewById(R.id.tv_school_qqqun);
//        tv_school_qqqun.setText("QQ:"+qqqun);
//        TextView  tv_school_title=(TextView) mView.findViewById(R.id.tv_school_title);
//        tv_school_title.setText(school_name+"学习交流群");
        ly_chat_email.setOnClickListener(mItemClickListener);
        ly_chat_to_person.setOnClickListener(mItemClickListener);
        ly_chat_to_group.setOnClickListener(mItemClickListener);

//        glideRequest = Glide.with(context);
//        glideRequest.load(UrlApi.ip_pic+url).into(img_school_icon);

    }


   private View.OnClickListener dismissListener = new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           dismiss();
       }
   };


}
