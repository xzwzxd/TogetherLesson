package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.bean.PinglunBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class NeighbourhoodDetialPingjaiAdapter extends BaseAdapter {

    private Context context;

    private List<PinglunBean> list=new ArrayList<>();

    public static String id;
    public NeighbourhoodDetialPingjaiAdapter(Context context, List<PinglunBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_my_neighbourhood_detial_pingjia, null);
            holder = new ViewHolder();
            holder.tv_pinglun_name = (TextView) convertView.findViewById(R.id.tv_pinglun_name);
            holder.tv_pinglun_content = (TextView) convertView.findViewById(R.id.tv_pinglun_content);

            /**对于listview，注意添加这一行，即可在item上使用高度**/
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_pinglun_name.setText(list.get(position).username);
        holder.tv_pinglun_content.setText(list.get(position).content);

        return convertView;
    }

    class ViewHolder {
        public TextView tv_pinglun_name,tv_pinglun_content;
    }
}
