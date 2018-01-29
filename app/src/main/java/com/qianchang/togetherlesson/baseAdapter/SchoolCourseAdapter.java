package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.qianchang.togetherlesson.bean.lessonlistBean;
import com.qianchang.togetherlesson.http.api.UrlApi;
import com.qianchang.togetherlesson.utils.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/9/28.
 */
public  class SchoolCourseAdapter extends BaseAdapter {
    private Context context;
    ItemFlagAdapter  mAdapter;
    private List<lessonlistBean.LessonlistBean.FlaglistBean> flaglistBeanList = new ArrayList<lessonlistBean.LessonlistBean.FlaglistBean>();
    private List<lessonlistBean.LessonlistBean> list = new ArrayList<lessonlistBean.LessonlistBean>();
    public static   String  id;
    public SchoolCourseAdapter(Context context, List<lessonlistBean.LessonlistBean> list) {

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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_school_course, null);
            holder = new ViewHolder();
            holder.tv_applynum = (TextView) convertView.findViewById(R.id.tv_applynum);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_coat = (TextView) convertView.findViewById(R.id.tv_coat);
            holder.picurl = (ImageView) convertView.findViewById(R.id.picurl);
            holder.grid_flag = (MyGridView) convertView.findViewById(R.id.grid_flag);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_applynum.setText(list.get(position).getApplynum());
        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_time.setText(list.get(position).getTimes());
        holder.tv_coat.setText("¥"+list.get(position).getCost());
        Glide.with(context).load(UrlApi.ip_pic+list.get(position).getPicurl()).placeholder(R.mipmap.zhanweitu5).into(holder.picurl);
        flaglistBeanList=list.get(position).getFlaglist();
        holder.grid_flag.setAdapter(new ItemFlagAdapter(context,flaglistBeanList));
        Log.d("xzw",""+flaglistBeanList.toString());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.SCHOOL_ID = list.get(position).getId();
                context.startActivity(new Intent(context, CouseDetialActivity.class).putExtra("id", MyApplication.SCHOOL_ID).putExtra("title", list.get(position).getTitle()));
            }
        });
        return convertView;
    }

    class ViewHolder {
        public TextView tv_applynum,tv_title,tv_time,tv_coat;
        private ImageView picurl;
        private MyGridView grid_flag;
    }

}
