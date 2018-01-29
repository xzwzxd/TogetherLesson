package com.qianchang.togetherlesson.baseAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.bean.CourseCopuse;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class CourseActivityAdapter extends BaseQuickAdapter<CourseCopuse.LessonBean.CouponBean, BaseViewHolder> {
    public CourseActivityAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseCopuse.LessonBean.CouponBean item) {
        helper.setText(R.id.mTvCompose, item.title);
    }

}
