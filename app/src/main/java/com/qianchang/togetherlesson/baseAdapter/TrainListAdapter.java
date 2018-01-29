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
import com.qianchang.togetherlesson.activty.SchoolDetialActivity1;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.bean.TrainBean;
import com.qianchang.togetherlesson.http.api.UrlApi;
import com.qianchang.togetherlesson.utils.MyGridView;

import java.util.List;

import static com.qianchang.togetherlesson.R.id.grid_flag;

/**
 * Created by Administrator on 2016/11/7 0007.
 */

public class TrainListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inf;

    private List<TrainBean> dataList;

    public TrainListAdapter(Context context, List<TrainBean> list) {
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

            convertView = inf.inflate(R.layout.item_train_list, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.picurl = (ImageView) convertView.findViewById(R.id.picurl);
            holder.collectnum = (TextView) convertView.findViewById(R.id.collectnum);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.grid_flag = (MyGridView) convertView.findViewById(grid_flag);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_title.setText(dataList.get(position).title);
        holder.collectnum.setText(dataList.get(position).collectnum);
        holder.tv_address.setText(dataList.get(position).address);
        Glide.with(context).load(UrlApi.ip_pic + dataList.get(position).picurl).placeholder(R.mipmap.zhanweitu5).into(holder.picurl);
        holder.grid_flag.setAdapter(new ZiSchoolFlagAdapter(context, dataList.get(position).stringList));
        holder.grid_flag.setTag(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.SCHOOL_ID= dataList.get(position).id;
                context. startActivity(new Intent(context,SchoolDetialActivity1.class).putExtra("name",dataList.get(position).title));
            }
        });
        return convertView;
    }
    private class ViewHolder {
        private ImageView picurl;
        public TextView tv_title, collectnum, tv_address;
        public MyGridView grid_flag;
    }
}
