package com.qianchang.togetherlesson.baseAdapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.bean.SchoolPicBean;
import com.qianchang.togetherlesson.http.api.UrlApi;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SchoolDetialAdapter extends BaseQuickAdapter<SchoolPicBean.PicarBean, BaseViewHolder> {
    public SchoolDetialAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SchoolPicBean.PicarBean item) {
        Glide.with(mContext).load(UrlApi.ip_pic+item.getImg()).crossFade().into((ImageView) helper.getView(R.id.img_school));
    }

}
