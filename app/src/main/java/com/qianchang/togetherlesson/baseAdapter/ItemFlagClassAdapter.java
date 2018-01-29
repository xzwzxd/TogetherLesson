package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.bean.FlaglistBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/7/23.
 */

public class ItemFlagClassAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public List<FlaglistBean> ziCommentBeanList = new ArrayList<>();
    ViewHolder holder = null;

    public ItemFlagClassAdapter(Context context, List<FlaglistBean> datas) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.ziCommentBeanList = datas;
    }
    @Override
    public int getCount() {
        return ziCommentBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return ziCommentBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_flag, null);
            holder = new ViewHolder();
            holder.tv_des = (TextView) convertView.findViewById(R.id.tv_des);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_des.setText(ziCommentBeanList.get(position).flagname);

        return convertView;
    }

    class ViewHolder {
        TextView tv_des;
    }
}
