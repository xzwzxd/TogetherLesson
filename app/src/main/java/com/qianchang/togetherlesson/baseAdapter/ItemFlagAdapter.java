package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.bean.lessonlistBean;

import java.util.List;

/**
 * Created by admin on 2017/7/23.
 */

public class ItemFlagAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<lessonlistBean.LessonlistBean.FlaglistBean>  mDatas;
    ViewHolder holder = null;

    public ItemFlagAdapter(Context context, List<lessonlistBean.LessonlistBean.FlaglistBean> datas) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mDatas = datas;
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
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
        holder.tv_des.setText(mDatas.get(position).getFlagname());

        return convertView;
    }

    class ViewHolder {
        TextView tv_des;
    }
}
