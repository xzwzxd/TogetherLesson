package com.qianchang.togetherlesson.utils;

import android.content.Context;
import android.content.Intent;


import com.qianchang.togetherlesson.activty.ImagesActivity;

import java.util.ArrayList;

/**
 * Created by maning on 16/3/3.
 * <p/>
 * 页面跳转
 */
public class IntentUtils {

    public static final String ImagePositionForImageShow = "PositionForImageShow";
    public static final String ImageArrayList = "BigImageArrayList";


    public static void startToImageShow(Context context, ArrayList<String> mDatas, int position) {
        Intent intent = new Intent(context.getApplicationContext(), ImagesActivity.class);
        intent.putStringArrayListExtra(ImageArrayList, mDatas);
        intent.putExtra(ImagePositionForImageShow, position);
        context.startActivity(intent);
    }
    public static void startAppShareText(Context context, String shareTitle, String shareText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain"); // 纯文本
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

}
