package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.bean.AllClassesBean;
import com.qianchang.togetherlesson.utils.MyGridView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2017/7/11.
 */

public class RightAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    List<AllClassesBean.AlllistBean.SonBeanX> mDatas = new ArrayList<>();
    ItemRightAdapter  mAdapter;
    ViewHolder holder = null;


    public RightAdapter(Context context, List<AllClassesBean.AlllistBean.SonBeanX> datas) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_classes, null);
            holder = new ViewHolder();
            holder.item_gridview_purchase = (MyGridView) convertView.findViewById(R.id.item_gridview_purchase);
            holder.tv = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        mAdapter=new ItemRightAdapter(mContext,mDatas.get(position).getSon());
        holder.item_gridview_purchase.setAdapter(mAdapter);
        holder.tv.setText(mDatas.get(position).getTitle());

        return convertView;
    }
    class ViewHolder{
        MyGridView item_gridview_purchase;
        TextView  tv;
    }

}
