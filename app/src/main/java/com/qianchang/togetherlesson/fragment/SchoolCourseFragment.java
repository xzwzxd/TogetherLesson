package com.qianchang.togetherlesson.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.AttachFragment;
import com.qianchang.togetherlesson.baseAdapter.SchoolCourseAdapter;
import com.qianchang.togetherlesson.bean.lessonlistBean;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 学校课程
 * Created by Administrator on 2017/4/22.
 */

public class SchoolCourseFragment extends AttachFragment {
    View view;
    @Bind(R.id.lv_all_part_time)
    ListView lvAllPartTime;
    private SchoolCourseAdapter schoolCourseAdapter;
    private List<lessonlistBean.LessonlistBean> orderBeanList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_school_course_list, container, false);
        ButterKnife.bind(this, view);
        getDate();
        return view;
    }

    private void getDate() {
        MyResponseHandler handler = new MyResponseHandler(getActivity(), false) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                Logger.json(responseString);
                orderBeanList.clear();
                lessonlistBean allClassesBean = lessonlistBean.objectFromData(responseString);
                orderBeanList = allClassesBean.getLessonlist();
                schoolCourseAdapter = new SchoolCourseAdapter(getActivity(), orderBeanList);
                lvAllPartTime.setAdapter(schoolCourseAdapter);
                schoolCourseAdapter.notifyDataSetChanged();
            }
        };
        ServerData.lessonbyschool(handler, MyApplication.SCHOOL_ID);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
