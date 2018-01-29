package com.qianchang.togetherlesson.baseAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.bean.MyComposeBean;

import java.util.List;

public class MyComposeAdapter extends BaseQuickAdapter<MyComposeBean.UsercouponBean, BaseViewHolder> {
    public MyComposeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyComposeBean.UsercouponBean item) {
//        helper.setText(R.id.mTvMoney, item.getTitle());
        helper.setText(R.id.mTvClass, item.getSchool());
        helper.setText(R.id.mTvCompose, item.getLesson());
        helper.setText(R.id.mTvTime, item.getEndtime());
    }
}