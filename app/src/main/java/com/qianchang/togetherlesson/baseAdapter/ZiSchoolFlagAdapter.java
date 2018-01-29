package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianchang.togetherlesson.R;

import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
public class ZiSchoolFlagAdapter extends BaseAdapter {

    private LayoutInflater inf;
    private Context context;
    private List<String> myPrintBeens;

    public ZiSchoolFlagAdapter(Context context, List<String> myPrintBeens) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inf.inflate(R.layout.item_zi_school_flag, null);
            holder = new ViewHolder();
            holder.tv_print_doc = (TextView) convertView.findViewById(R.id.tv_print_doc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_print_doc.setText(myPrintBeens.get(position));
        return convertView;
    }

    private class ViewHolder {
        public TextView tv_print_doc;
    }
}
