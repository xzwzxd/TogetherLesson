package com.qianchang.togetherlesson.simpleAdapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.bean.IndexBean;
import com.qianchang.togetherlesson.http.api.UrlApi;
import com.qianchang.togetherlesson.utils.GlideCircleTransform;

import java.util.List;

public class HomeCourseAdapter extends BaseQuickAdapter<IndexBean.CourseslistBean, BaseViewHolder> {
    public HomeCourseAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }
    private RequestManager glideRequest;

    @Override
    protected void convert(BaseViewHolder helper, IndexBean.CourseslistBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        glideRequest = Glide.with(mContext);
        glideRequest.load(UrlApi.ip_pic + item.getPicurl()).placeholder(R.mipmap.zhanweitu5).transform(new GlideCircleTransform(mContext)).into((ImageView) helper.getView(R.id.img_course));
    }
}
