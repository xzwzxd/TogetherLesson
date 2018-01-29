package com.qianchang.togetherlesson.simpleAdapter;

import android.util.Log;
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

public class HomeNewsAdapter extends BaseQuickAdapter<IndexBean.NewslistBean, BaseViewHolder> {
    public HomeNewsAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }
    private RequestManager glideRequest;

    @Override
    protected void convert(BaseViewHolder helper, IndexBean.NewslistBean item) {
        Log.e("xzw",item.getTitle());
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.hits, item.getHits());
        helper.setText(R.id.author, item.getAuthor());
        helper.setText(R.id.collectnum, item.getCollectnum());
        glideRequest = Glide.with(mContext);
        glideRequest.load(UrlApi.ip_pic + item.getPicurl()).placeholder(R.mipmap.zhanweitu5).transform(new GlideCircleTransform(mContext)).into((ImageView) helper.getView(R.id.picarr));
        Glide.with(mContext).load(UrlApi.ip_pic + item.getPicarr()).into((ImageView)helper.getView(R.id.picurl));
    }
}
