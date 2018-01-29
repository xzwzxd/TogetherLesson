package com.qianchang.togetherlesson.baseAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qianchang.togetherlesson.bean.ThemeBean;
import com.qianchang.togetherlesson.fragment.PageFragment;

import java.util.List;


/**
 * Created by lizhaotailang on 2016/3/21.
 * theme pager adapter
 */
public class ThemePagerAdapter extends FragmentPagerAdapter {

    private List<ThemeBean> list;
    private Context context;

    public ThemePagerAdapter(FragmentManager fm, Context context, List<ThemeBean> list) {
        super(fm);
        this.list = list;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).title;
    }
}
