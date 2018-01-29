package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.bean.LeftSelectBean;

import java.util.List;

/**
 * Created by HT on 2017/8/2.
 */

public class LeftSelectAdapter extends RecyclerView.Adapter<LeftSelectAdapter.ViewHolder> {

    private Context context;
    private List<LeftSelectBean> list;
    int selection = -1;

    public LeftSelectAdapter(Context context, List<LeftSelectBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_city, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.tv_city.setText(list.get(position).title);
        if (position == selection) {
            viewHolder.tv_city.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            viewHolder.rl_left.setBackgroundResource(R.color.pop_item_select);
        } else {
            viewHolder.tv_city.setTextColor(context.getResources().getColor(R.color.pop_nomal));
            viewHolder.rl_left.setBackgroundResource(R.color.colorWindowBg);
        }

        //判断是否设置了监听器
        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(viewHolder.itemView,position); // 2
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_city;
        LinearLayout rl_left;

        public ViewHolder(View view) {
            super(view);
            tv_city = (TextView) view.findViewById(R.id.tv_city);
            rl_left = (LinearLayout) view.findViewById(R.id.rl_left);
        }
    }

    /**
     * ItemClick的回调接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }
}
