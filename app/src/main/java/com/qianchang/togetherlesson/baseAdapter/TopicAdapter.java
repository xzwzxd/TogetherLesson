package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.activty.TopicDetialActivity;
import com.qianchang.togetherlesson.bean.ClubListBean;
import com.qianchang.togetherlesson.http.api.UrlApi;
import com.qianchang.togetherlesson.utils.GlideCircleTransform;
import com.qianchang.togetherlesson.utils.IntentUtils;
import com.qianchang.togetherlesson.utils.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
public class TopicAdapter extends BaseAdapter {

    private LayoutInflater inf;
    private Context context;
    private List<ClubListBean> clubListBeanList;
    private List<String> stringList = new ArrayList<>();

    final int TYPE_1 = 0;  //正常多图布局
    final int TYPE_2 = 1;  //一个图片布局
//    final int TYPE_3 = 3;  //没有图片布局
    int type;

    public TopicAdapter(Context context, List<ClubListBean> myPrintBeens) {
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        type = clubListBeanList.get(position).stringList.size();

        if (type > 1) {
            return TYPE_1;
        } else {
            return TYPE_2;

        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        inf = LayoutInflater.from(context);
        int type = getItemViewType(position);
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;

        if (convertView == null) {

            switch (type) {

                case TYPE_1:
                    convertView = inf.inflate(R.layout.item_club, null);
                    viewHolder1 = new ViewHolder1(convertView);
                    convertView.setTag(viewHolder1);

                    break;

                case TYPE_2:
                    convertView = inf.inflate(R.layout.item_club_single, null);
                    viewHolder2 = new ViewHolder2(convertView);
                    convertView.setTag(viewHolder2);
                    break;
            }


        } else {

            switch (type) {

                case TYPE_2:
                    viewHolder2 = ((ViewHolder2) convertView.getTag());
                    break;
                case TYPE_1:
                    viewHolder1 = ((ViewHolder1) convertView.getTag());
                    break;
            }

        }
        switch (type) {

            case TYPE_1:
                viewHolder1.id_item_name.setText(clubListBeanList.get(position).username);
                viewHolder1.item_title.setText(clubListBeanList.get(position).content);
                viewHolder1.tv_replay_num.setText(clubListBeanList.get(position).pinglunnum);
                viewHolder1.id_item_posttipme.setText(clubListBeanList.get(position).posttime);
                viewHolder1.glideRequest = Glide.with(context);
                viewHolder1.glideRequest.load(UrlApi.ip_pic + clubListBeanList.get(position).avatar).placeholder(R.mipmap.zhanweitu5).transform(new GlideCircleTransform(context)).into(viewHolder1.img_icon);
                viewHolder1.grid_pic.setAdapter(new ZiTopicAdapter(context, clubListBeanList.get(position).stringList));
                Logger.d(UrlApi.ip_pic + clubListBeanList.get(position).avatar);
                viewHolder1.grid_pic.setTag(position);

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, TopicDetialActivity.class).putExtra("id", clubListBeanList.get(position).id));
                    }
                });

                viewHolder1.grid_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                        ArrayList<String> arrayList = new ArrayList<>();
                        for (int j = 0; j < clubListBeanList.get(position).stringList.size(); j++) {
                            arrayList.add(clubListBeanList.get(position).stringList.get(j));
                        }
                        IntentUtils.startToImageShow(context, arrayList, i);
                    }
                });
                break;

            case TYPE_2:

                viewHolder2.id_item_name.setText(clubListBeanList.get(position).username);
                viewHolder2.item_title.setText(clubListBeanList.get(position).content);
                viewHolder2.tv_replay_num.setText(clubListBeanList.get(position).pinglunnum);
                viewHolder2.id_item_posttipme.setText(clubListBeanList.get(position).posttime);
                viewHolder2.glideRequest = Glide.with(context);
                viewHolder2.glideRequest.load(UrlApi.ip_pic + clubListBeanList.get(position).avatar).placeholder(R.mipmap.zhanweitu5).transform(new GlideCircleTransform(context)).into(viewHolder2.img_icon);
                Logger.d(UrlApi.ip_pic + clubListBeanList.get(position).avatar);
                if (clubListBeanList.get(position).stringList.size()==0){

                    viewHolder2.img_single.setVisibility(View.GONE);
                }else{
                    viewHolder2.img_single.setVisibility(View.VISIBLE);
                    Glide.with(context).load(UrlApi.ip_pic + clubListBeanList.get(position).stringList.get(0)).into(viewHolder2.img_single);
                }
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, TopicDetialActivity.class).putExtra("id", clubListBeanList.get(position).id));
                    }
                });
                break;
        }
        return convertView;
    }


    class ViewHolder1 {

        private RequestManager glideRequest;
        private TextView id_item_name, item_title, tv_replay_num, id_item_posttipme;
        private ImageView img_icon;
        private MyGridView grid_pic;

        public ViewHolder1(View convertView) {

            id_item_name = (TextView) convertView.findViewById(R.id.id_item_name);
            item_title = (TextView) convertView.findViewById(R.id.item_title);
            tv_replay_num = (TextView) convertView.findViewById(R.id.tv_replay_num);
            id_item_posttipme = (TextView) convertView.findViewById(R.id.id_item_posttipme);
            img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
            grid_pic = (MyGridView) convertView.findViewById(R.id.grid_pic);
        }
    }

    class ViewHolder2 {

        private RequestManager glideRequest;
        private TextView id_item_name, item_title, tv_replay_num, id_item_posttipme;
        private ImageView img_icon;
        private ImageView img_single;

        public ViewHolder2(View convertView) {

            id_item_name = (TextView) convertView.findViewById(R.id.id_item_name);
            item_title = (TextView) convertView.findViewById(R.id.item_title);
            tv_replay_num = (TextView) convertView.findViewById(R.id.tv_replay_num);
            id_item_posttipme = (TextView) convertView.findViewById(R.id.id_item_posttipme);
            img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
            img_single = (ImageView) convertView.findViewById(R.id.img_single);
        }
    }

}
