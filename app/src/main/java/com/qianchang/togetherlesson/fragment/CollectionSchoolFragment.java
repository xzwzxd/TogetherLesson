package com.qianchang.togetherlesson.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.activty.SchoolDetialActivity1;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.AttachFragment;
import com.qianchang.togetherlesson.baseAdapter.TrainListAdapter;
import com.qianchang.togetherlesson.bean.TrainBean;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.youth.xframe.widget.XToast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/6/17.
 */

public class CollectionSchoolFragment extends AttachFragment {

    View view;
    @Bind(R.id.id_recyclerview)
    ListView idRecyclerview;
    @Bind(R.id.ly_chat)
    LinearLayout lyChat;
    @Bind(R.id.ly_have_class)
    LinearLayout lyHaveClass;
    public OnR onR;
    String flag;
    private List<TrainBean> mDatas = new ArrayList<>();
    private TrainListAdapter trainListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommend, container, false);
        ButterKnife.bind(this, view);

        trainListAdapter = new TrainListAdapter(getActivity(), mDatas);
        idRecyclerview.setAdapter(trainListAdapter);

        if (!MyApplication.P2PPreferences.getUid().equals("")) {
            getBanner();
        }
        idRecyclerview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyApplication.SCHOOL_ID = mDatas.get(i).id;
                startActivity(new Intent(getActivity(), SchoolDetialActivity1.class).putExtra("id", MyApplication.SCHOOL_ID).putExtra("toast","取消收藏成功"));

            }
        });

        idRecyclerview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("确认删除")
                        .setMessage("确定吗？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getCollection(String.valueOf(mDatas.get(position).id));
                                mDatas.remove(position);
                                trainListAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();


                return true;
            }
        });
        onR = new OnR() {
            @Override
            public void onR() {
                getBanner();
            }
        };
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public interface OnR {
        public void onR();
    }

    @Override
    public void onResume() {
        if (!MyApplication.P2PPreferences.getUid().equals("")) {
            getBanner();
        }
        super.onResume();
    }

    private void getBanner() {
        MyResponseHandler handler = new MyResponseHandler(getActivity(), true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                mDatas.clear();
                super.onSuccess(statusCode, headers, responseString);
                Logger.json(responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    JSONArray bannerjsonArray = jsonObject.optJSONArray("schoollist");
                    for (int i = 0; i < bannerjsonArray.length(); i++) {
                        JSONObject object = bannerjsonArray.optJSONObject(i);
                        TrainBean bannerBean = new TrainBean();
                        bannerBean.id = object.optString("id");
                        bannerBean.title = object.optString("title");
                        bannerBean.picurl = object.optString("picurl");
                        bannerBean.collectnum = object.optString("collectnum");
                        bannerBean.address = object.optString("address");
                        bannerBean.flag = object.optString("flag");
                        flag = object.optString("flag");
                        mDatas.add(bannerBean);
                    }
                    trainListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.mycollectschool(handler, MyApplication.P2PPreferences.getUid());
    }


    private void getCollection(String deleteId) {

        MyResponseHandler handler = new MyResponseHandler(getActivity(), true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                Logger.json(responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    if (jsonObject.optString("message").equals("1")) {
                        XToast.success("删除成功");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.collectschool(handler, MyApplication.P2PPreferences.getUid(), deleteId);
    }

}
