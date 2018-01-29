package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.http.api.UrlApi;

import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
public  class ZiTopicAdapter extends BaseAdapter {

    private LayoutInflater inf;
    private Context context;
    private List<String> myPrintBeens;
    public ZiTopicAdapter(Context context, List<String> myPrintBeens) {
        this.context = context;
        this.myPrintBeens = myPrintBeens;
        inf = LayoutInflater.from(this.context);
    }
    @Override
    public int getCount() {
        return myPrintBeens.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inf.inflate(R.layout.item_zi_topic, null);
            holder = new ViewHolder();
            holder.item_print_doc = (ImageView) convertView.findViewById(R.id.item_print_doc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(UrlApi.ip_pic+myPrintBeens.get(position)).placeholder(R.mipmap.zhanweitu5).into(holder.item_print_doc);
        Logger.d(UrlApi.ip_pic+myPrintBeens.get(position));

        return convertView;
    }
    private class ViewHolder {
        public ImageView item_print_doc;
    }
}
