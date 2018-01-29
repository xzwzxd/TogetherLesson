package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.activty.CouseDetialActivity;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.bean.ClassesBean;
import com.qianchang.togetherlesson.http.api.UrlApi;
import com.qianchang.togetherlesson.utils.MyGridView;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7 0007.
 */

public class ClassesListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inf;
    private List<ClassesBean> dataList;

    public ClassesListAdapter(Context context, List<ClassesBean> list) {
        this.context = context;
        this.dataList = list;
        inf = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {

            convertView = inf.inflate(R.layout.item_class_list, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.picurl = (ImageView) convertView.findViewById(R.id.picurl);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_coat = (TextView) convertView.findViewById(R.id.tv_coat);
            holder.tv_applynum = (TextView) convertView.findViewById(R.id.tv_applynum);
            holder.grid_flag = (MyGridView) convertView.findViewById(R.id.grid_flag);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_applynum.setText(dataList.get(position).applynum + "人报名");
        holder.tv_title.setText(dataList.get(position).title);
        holder.tv_coat.setText("¥" + dataList.get(position).cost);
        holder.tv_time.setText(dataList.get(position).times);
        Glide.with(context).load(UrlApi.ip_pic + dataList.get(position).picurl).placeholder(R.mipmap.zhanweitu5).into(holder.picurl);
        holder.grid_flag.setAdapter(new ItemFlagClassAdapter(context, dataList.get(position).ziCommentBeanList));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.SCHOOL_ID = dataList.get(position).id;
                context.startActivity(new Intent(context, CouseDetialActivity.class).putExtra("id", MyApplication.SCHOOL_ID).putExtra("title", dataList.get(position).title));
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView picurl;
        public TextView tv_title, tv_time, tv_coat, tv_applynum;
        private MyGridView grid_flag;

    }
}
