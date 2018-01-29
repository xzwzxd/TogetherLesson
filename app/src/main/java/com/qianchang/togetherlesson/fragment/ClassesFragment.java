package com.qianchang.togetherlesson.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.baseAdapter.LeftAdapter;
import com.qianchang.togetherlesson.baseAdapter.RightAdapter;
import com.qianchang.togetherlesson.bean.AllClassesBean;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/7/23.
 */

public class ClassesFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView leftList;
    ListView rightList;
    @Bind(R.id.tv_title_name)
    TextView tvTitleName;
    @Bind(R.id.img_title_back)
    ImageView imgTitleBack;
    private View view;
    private LeftAdapter leftAdapter;
    private List<AllClassesBean.AlllistBean> classesBeanList = new ArrayList<>();
    private String coursesid = "0";
    RightAdapter rightAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frament_classes, null);
        ButterKnife.bind(this, view);
        tvTitleName.setText("分类");
        imgTitleBack.setVisibility(View.GONE);
        leftList = (ListView) view.findViewById(R.id.left_list);
        leftList.setOnItemClickListener(this);
        rightList = (ListView) view.findViewById(R.id.right_list);
        MyResponseHandler handler = new MyResponseHandler(getActivity(), true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                super.onSuccess(statusCode, headers, responseString);
                Logger.json(responseString);
                AllClassesBean allClassesBean = AllClassesBean.objectFromData(responseString);
                classesBeanList = allClassesBean.getAlllist();
                leftAdapter = new LeftAdapter(getActivity(), classesBeanList);
                leftList.setAdapter(leftAdapter);
                leftAdapter.setSelection(0);
                rightAdapter = new RightAdapter(getActivity(), classesBeanList.get(0).getSon());
                rightList.setAdapter(rightAdapter);
                rightAdapter.notifyDataSetChanged();
                leftAdapter.notifyDataSetChanged();
            }
        };
        ServerData.allcourses(handler, coursesid);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        leftAdapter.setSelection(position);
        leftAdapter.notifyDataSetChanged();
        rightAdapter = new RightAdapter(getActivity(), classesBeanList.get(position).getSon());
        rightList.setAdapter(rightAdapter);
        rightAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
