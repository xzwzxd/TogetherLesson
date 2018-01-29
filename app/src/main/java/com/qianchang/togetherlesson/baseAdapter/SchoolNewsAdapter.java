package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.bean.NewsBean;
import com.qianchang.togetherlesson.bean.SchoolNewsNean;
import com.qianchang.togetherlesson.http.api.UrlApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/7 0007.
 */

public class SchoolNewsAdapter extends BaseAdapter {
    private Context context;

    private List<SchoolNewsNean> list = new ArrayList<>();
    public static   String  id;
    public SchoolNewsAdapter(Context context, List<SchoolNewsNean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       ViewHolder holder = null;
        if (holder == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.picarr = (ImageView) convertView.findViewById(R.id.picarr);
            holder.picurl = (ImageView) convertView.findViewById(R.id.picurl);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.collectnum = (TextView) convertView.findViewById(R.id.collectnum);
            holder.hits = (TextView) convertView.findViewById(R.id.hits);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(list.get(position).title);
        holder.author.setText(list.get(position).author);
        holder.collectnum.setText(list.get(position).collectnum);
        holder.hits.setText(list.get(position).hits);
        Glide.with(context).load(UrlApi.ip_pic+list.get(position).picarr).placeholder(R.mipmap.zhanweitu5).into( holder.picarr);
        Glide.with(context).load(UrlApi.ip_pic+list.get(position).picurl).placeholder(R.mipmap.zhanweitu5).into( holder.picurl);

        return convertView;
    }

    class ViewHolder {
        private ImageView picarr,picurl;
        public TextView tv_title,author,collectnum,hits;
    }
}
