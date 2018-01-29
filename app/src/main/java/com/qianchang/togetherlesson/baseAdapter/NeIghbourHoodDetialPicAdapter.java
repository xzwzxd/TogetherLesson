package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.http.api.UrlApi;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class NeIghbourHoodDetialPicAdapter extends BaseAdapter {

    private Context context;

    private List<String> listStr = new ArrayList<>();

    public static String id;
    public NeIghbourHoodDetialPicAdapter(Context context, List<String> str_aay) {
        this.context = context;
        this.listStr = str_aay;
    }

    @Override
    public int getCount() {
        return listStr.size();
    }

    @Override
    public Object getItem(int position) {
        return listStr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (holder == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_my_neighbourhood_detial_pic, null);
            holder = new ViewHolder();

            holder.img_neighbourhood_detaial_dianzan= (ImageView) convertView.findViewById(R.id.img_neighbourhood_detaial_dianzan);
            /**对于listview，注意添加这一行，即可在item上使用高度**/
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(UrlApi.ip_pic+listStr.get(position)).centerCrop().into( holder.img_neighbourhood_detaial_dianzan);

        return convertView;
    }

    class ViewHolder {
        public ImageView img_neighbourhood_detaial_dianzan;
    }

}
