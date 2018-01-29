package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.activty.ClassesListActivity;
import com.qianchang.togetherlesson.bean.AllClassesBean;

import java.util.List;

/**
 * Created by admin on 2017/7/23.
 */

public class ItemRightAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<AllClassesBean.AlllistBean.SonBeanX.SonBean>  mDatas;
    ViewHolder holder = null;

    public ItemRightAdapter(Context context, List<AllClassesBean.AlllistBean.SonBeanX.SonBean> datas) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_zi_classes, null);
            holder = new ViewHolder();
            holder.tv_des = (TextView) convertView.findViewById(R.id.tv_des);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_des.setText(mDatas.get(position).getTitle());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ClassesListActivity.class).putExtra("title",mDatas.get(position).getTitle()).putExtra("id",mDatas.get(position).getId()));
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_des;
    }
}
