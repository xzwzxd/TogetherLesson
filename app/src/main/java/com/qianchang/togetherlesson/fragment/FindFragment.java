package com.qianchang.togetherlesson.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.activty.TopicActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2016/10/9.
 */
public class FindFragment extends Fragment {

    View view;
    @Bind(R.id.ly_help_student)
    LinearLayout lyHelpStudent;
    @Bind(R.id.tv_title_name)
    TextView tvTitleName;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.img_title_back)
    ImageView imgTitleBack;
    @Bind(R.id.tv_right)
    TextView tvRight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find, container, false);
        ButterKnife.bind(this, view);
        tvTitleName.setText("发现");
        imgTitleBack.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ly_help_student})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_help_student:
                startActivity(new Intent(getActivity(), TopicActivity.class));
                break;
        }
    }
}
