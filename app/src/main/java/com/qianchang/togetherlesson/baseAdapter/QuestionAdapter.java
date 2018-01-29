package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.bean.QuestionlistBean;
import com.qianchang.togetherlesson.bean.ZiSchoolCourseBean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
public  class QuestionAdapter extends BaseAdapter {

    private LayoutInflater inf;

    private Context context;

    private List<QuestionlistBean> myPrintBeens;

    public QuestionAdapter(Context context, List<QuestionlistBean> myPrintBeens) {

        this.context = context;

        this.myPrintBeens = myPrintBeens;

        inf = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myPrintBeens.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {

            convertView = inf.inflate(R.layout.item_question, null);
            holder = new ViewHolder();
            holder.tv_qustion = (TextView) convertView.findViewById(R.id.tv_qustion);
            holder.tv_answer = (TextView) convertView.findViewById(R.id.tv_answer);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_qustion.setText(myPrintBeens.get(position).title);
        holder.tv_answer.setText(myPrintBeens.get(position).content);

        return convertView;
    }
    private class ViewHolder {
        public TextView tv_qustion,tv_answer;
    }
}
