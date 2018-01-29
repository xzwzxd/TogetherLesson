package com.qianchang.togetherlesson.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.baseAdapter.SchoolDetialAdapter;
import com.qianchang.togetherlesson.bean.SchoolPicBean;
import com.qianchang.togetherlesson.bean.SchoolPicBean.PicarBean;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.utils.IntentUtils;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/22 0022.
 */

public class SchoolPicFragment extends Fragment {
    View view;
    private RecyclerView mRecyclerView;
    private SchoolDetialAdapter schoolPicAdapterr;
    private List<PicarBean> schoolPicBeanList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_school_pic, container, false);

        initView();
        initData();
        return view;
    }

    private void initView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_school_pic);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void initData() {

        MyResponseHandler handler = new MyResponseHandler(getActivity(), false) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);

                Logger.json(responseString);
                SchoolPicBean schoolPicBean = SchoolPicBean.objectFromData(responseString);
                schoolPicBeanList = schoolPicBean.getPicar();
                schoolPicAdapterr = new SchoolDetialAdapter(R.layout.item_school_detial, schoolPicBeanList);
                schoolPicAdapterr.openLoadAnimation();
                mRecyclerView.setAdapter(schoolPicAdapterr);
                schoolPicAdapterr.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        ArrayList<String> arrayList = new ArrayList<>();
                        for (int j = 0; j < schoolPicBeanList.size(); j++) {
                            arrayList.add(schoolPicBeanList.get(j).getImg());
                        }
                        IntentUtils.startToImageShow(getActivity(), arrayList, position);
                    }
                });
            }
        };
        ServerData.trainschoolphoto(handler, MyApplication.SCHOOL_ID);
    }
}

