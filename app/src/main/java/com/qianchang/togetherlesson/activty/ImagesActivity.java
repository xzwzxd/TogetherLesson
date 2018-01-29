package com.qianchang.togetherlesson.activty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.http.api.UrlApi;
import com.qianchang.togetherlesson.utils.IntentUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImagesActivity extends Activity {

    private static final String TAG = ImagesActivity.class.getSimpleName();
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tv_showNum)
    TextView tvShowNum;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private Context mContext;

    private ArrayList<String> mDatas = new ArrayList<>();
    private int startIndex;
    private TouchImageAdapter touchImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);
        ButterKnife.bind(this);
        mContext = this;
        initIntent();
        initData();
        //初始化ViewPager
        initViewPager();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViewPager() {
        touchImageAdapter = new TouchImageAdapter(mContext, mDatas);
        viewPager.setAdapter(touchImageAdapter);
        if (startIndex > 0) {
            viewPager.setCurrentItem(startIndex);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvShowNum.setText((position + 1) + "/" + mDatas.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initIntent() {
        //获取传递的数据
        Intent intent = getIntent();
        mDatas = intent.getStringArrayListExtra(IntentUtils.ImageArrayList);
        startIndex = intent.getIntExtra(IntentUtils.ImagePositionForImageShow, 0);
    }
    private void initData() {
        tvShowNum.setText((startIndex + 1) + "/" + mDatas.size());
    }
    static class TouchImageAdapter extends PagerAdapter {
        private Context mContext;
        private ArrayList<String> mDatas;
        private LayoutInflater layoutInflater;
        private View mCurrentView;
        public TouchImageAdapter(Context mContext, ArrayList<String> mDatas) {
            this.mContext = mContext;
            this.mDatas = mDatas;
            layoutInflater = LayoutInflater.from(this.mContext);
        }
        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            mCurrentView = (View) object;
        }
        public View getPrimaryItem() {
            return mCurrentView;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            final String imageUrl = mDatas.get(position);
            View inflate = layoutInflater.inflate(R.layout.item_show_image, container, false);
            final ImageView imageView = (ImageView) inflate.findViewById(R.id.imageView);
            Glide.with(mContext).load(UrlApi.ip_pic+imageUrl).thumbnail(0.2f).into(imageView);
            Log.d("图片","+++++++"+UrlApi.ip_pic+imageUrl);
            container.addView(inflate);
            return inflate;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    protected void onDestroy() {
        //清空集合
        if (mDatas != null) {
            mDatas.clear();
            mDatas = null;
        }
        super.onDestroy();
    }
}
