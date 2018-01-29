package com.qianchang.togetherlesson.activty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.bean.SearchHistorysBean;
import com.qianchang.togetherlesson.db.SearchHistorysDao;
import com.youth.xframe.widget.XToast;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;


/**
 * 历史搜索
 * Created by admin on 2017/4/2.
 */

public class HistorySearchActivity extends Activity implements View.OnClickListener {
    Context context = this;
    private EditText et_search_keyword;
    private ImageView btn_search;
    private ImageView tv_back;
    private TextView tv_clear;
    private GridView lv_history_word;

    private SearchHistorysDao dao;
    private ArrayList<SearchHistorysBean> historywordsList = new ArrayList<SearchHistorysBean>();
    private SearchHistoryAdapter mAdapter;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_search);

        dao = new SearchHistorysDao(this);
        historywordsList = dao.findAll();

        initView();
    }

    private void initView() {
        et_search_keyword = (EditText) findViewById(R.id.et_search_keyword);

        btn_search = (ImageView) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);

        tv_back = (ImageView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);

        tv_clear = (TextView) findViewById(R.id.tv_clear);
        tv_clear.setOnClickListener(this);

        lv_history_word = (GridView) findViewById(R.id.lv_history_word);
        mAdapter = new SearchHistoryAdapter(historywordsList);
        count = mAdapter.getCount();
        if (count > 0) {

            tv_clear.setVisibility(View.VISIBLE);
        } else {
            tv_clear.setVisibility(View.GONE);
        }
        lv_history_word.setAdapter(mAdapter);
        lv_history_word.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SearchHistorysBean bean = (SearchHistorysBean) mAdapter.getItem(position);
                et_search_keyword.setText(bean.historyword);
                mAdapter.setSeclection(position);

                mAdapter.notifyDataSetChanged();
                btn_search.performClick();
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.btn_search:// 点击搜索，先拿关键词
                String searchWord = et_search_keyword.getText().toString().trim();
                if (searchWord.equals("")) {
                    XToast.warning("请输入内容");
                    return;
                } else {
                    // 存储数据
                    dao.addOrUpdate(searchWord);
                    historywordsList.clear();
                    historywordsList.addAll(dao.findAll());
                    mAdapter.notifyDataSetChanged();
                    tv_clear.setVisibility(View.VISIBLE);
                    startActivity(new Intent(context, ClassesListActivity.class).putExtra("keywords", et_search_keyword.getText().toString()).putExtra("type", "2"));
                }
                break;
            case R.id.tv_clear:
                // 点击清除历史的时候：清除数据库中所有的数据
                dao.deleteAll();
                historywordsList.clear();
                mAdapter.notifyDataSetChanged();
                tv_clear.setVisibility(View.GONE);
                break;
            default:
                finish();
                break;
        }

    }

    private int clickTemp = -1;

    class SearchHistoryAdapter extends BaseAdapter {

        private ArrayList<SearchHistorysBean> historywordsList;

        public SearchHistoryAdapter(ArrayList<SearchHistorysBean> historywordsList) {
            this.historywordsList = historywordsList;
        }

        public void setSeclection(int position) {
            clickTemp = position;
        }

        @Override
        public int getCount() {

            return historywordsList == null ? 0 : historywordsList.size();
        }

        @Override
        public Object getItem(int position) {

            return historywordsList.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(HistorySearchActivity.this).inflate(R.layout.item_search_history, null);
                holder.tv_word = (TextView) convertView.findViewById(R.id.tv_search_record);
                holder.text_bg = (LinearLayout) convertView.findViewById(R.id.text_bg);

                convertView.setTag(holder);
                AutoUtils.autoSize(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_word.setText(historywordsList.get(position).toString());
            if (clickTemp == position) { //选中
                holder.text_bg.setBackgroundResource(R.drawable.history_text_border);
                holder.tv_word.setTextColor(getResources().getColor(R.color.text_hsitory_select));
            } else {  //没选中
                holder.text_bg.setBackgroundResource(R.drawable.history_text_border_select);
                holder.tv_word.setTextColor(getResources().getColor(R.color.text_hsitory));
            }
            return convertView;
        }
    }
    class ViewHolder {
        TextView tv_word;
        LinearLayout text_bg;
    }

}
