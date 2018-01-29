package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.bean.AllClassesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
public class LeftAdapter extends BaseAdapter {

    private LayoutInflater inf;
    private Context context;
    private List<AllClassesBean.AlllistBean> clubListBeanList;
    private List<String> stringList = new ArrayList<>();
    int selection = 0;
//    public List<Son> sonList = new ArrayList<>();
    List<AllClassesBean.AlllistBean.SonBeanX> son = new ArrayList<>();
    RightAdapter rightAdapter;

    public LeftAdapter(Context context, List<AllClassesBean.AlllistBean> myPrintBeens) {
        this.context = context;
        this.clubListBeanList = myPrintBeens;
        inf = LayoutInflater.from(this.context);

    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    @Override
    public int getCount() {
        return clubListBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return clubListBeanList.get(position);
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

            convertView = inf.inflate(R.layout.item_left_classes, null);
            holder = new ViewHolder();
            holder.rl_left = (RelativeLayout) convertView.findViewById(R.id.rl_left);
            holder.tv_catogray = (TextView) convertView.findViewById(R.id.tv_catogray);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Logger.d(son.size());

        if (position == selection) {
            holder.tv_catogray.setBackgroundResource(R.drawable.rec_red_left_stroke);
            holder.tv_catogray.setTextColor(context.getResources().getColor(R.color.black));
            holder.rl_left.setBackgroundResource(R.color.white);
//            holder.rl_left.setBackgroundResource(Color.parseColor(#sdsds));
        } else {

            holder.tv_catogray.setBackgroundResource(R.drawable.empty);
            holder.rl_left.setBackgroundResource(R.color.left_title);

        }
        holder.tv_catogray.setText(clubListBeanList.get(position).getTitle());

       /* convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightAdapter = new RightAdapter(context, son);
                listView.setAdapter(rightAdapter);
                Logger.d("点击aaaaaaaa");
                son = clubListBeanList.get(position).getSon();
                rightAdapter.notifyDataSetChanged();
            }
        });*/
        return convertView;
    }

    private class ViewHolder {

        RelativeLayout rl_left;
        TextView tv_catogray;
    }
}
