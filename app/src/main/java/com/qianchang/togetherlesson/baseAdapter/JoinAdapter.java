package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.bean.ClubListBean;
import com.qianchang.togetherlesson.bean.MyJoinTopicBean;
import com.qianchang.togetherlesson.http.api.UrlApi;
import com.qianchang.togetherlesson.utils.GlideCircleTransform;
import com.qianchang.togetherlesson.utils.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
public class JoinAdapter extends BaseAdapter {

    private LayoutInflater inf;
    private Context context;
    private List<MyJoinTopicBean> clubListBeanList;
    private List<String> stringList = new ArrayList<>();

    public JoinAdapter(Context context, List<MyJoinTopicBean> myPrintBeens) {
        this.context = context;
        this.clubListBeanList = myPrintBeens;
        inf = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return clubListBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        stringList.clear();
        ViewHolder holder = null;

        if (convertView == null) {

            convertView = inf.inflate(R.layout.item_club, null);
            holder = new ViewHolder();
            holder.id_item_name = (TextView) convertView.findViewById(R.id.id_item_name);
            holder.item_title = (TextView) convertView.findViewById(R.id.item_title);
            holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
            holder.grid_pic = (MyGridView) convertView.findViewById(R.id.grid_pic);
            convertView.setTag(holder);
            holder.grid_pic.setTag(position);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.id_item_name.setText(clubListBeanList.get(position).username);
        holder.item_title.setText(clubListBeanList.get(position).content);
        holder.glideRequest = Glide.with(context);
        holder.glideRequest.load(UrlApi.ip_pic + clubListBeanList.get(position).avatar).placeholder(R.mipmap.zhanweitu5).transform(new GlideCircleTransform(context)).into(holder.img_icon);
        holder.grid_pic.setAdapter(new ZiTopicAdapter(context, clubListBeanList.get(position).stringList));

        holder.grid_pic.setTag(position);
        return convertView;
    }

    private class ViewHolder {
        private RequestManager glideRequest;
        private TextView id_item_name, item_title;
        private ImageView img_icon;
        private MyGridView grid_pic;
    }
}
