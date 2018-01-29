package com.qianchang.togetherlesson.activty;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.baseAdapter.ClassesListAdapter;
import com.qianchang.togetherlesson.baseAdapter.LeftSelectAdapter;
import com.qianchang.togetherlesson.baseAdapter.TrainListAdapter;
import com.qianchang.togetherlesson.bean.ClassesBean;
import com.qianchang.togetherlesson.bean.FlaglistBean;
import com.qianchang.togetherlesson.bean.LeftSelectBean;
import com.qianchang.togetherlesson.bean.TrainBean;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.qianchang.togetherlesson.utils.DividerDecoration;
import com.qianchang.togetherlesson.widget.XListView;
import com.youth.xframe.widget.XToast;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/7/27.
 */

public class ClassesListActivity extends BaseSwipeBackCompatActivity implements XListView.IXListViewListener {

    @Bind(R.id.tv_left_classes)
    TextView tvLeftClasses;
    @Bind(R.id.img_left_classes)
    ImageView imgLeftClasses;
    @Bind(R.id.ly_left_classes)
    LinearLayout lyLeftClasses;
    @Bind(R.id.tv_middle_classes)
    TextView tvMiddleClasses;
    @Bind(R.id.img_middle_classes)
    ImageView imgMiddleClasses;
    @Bind(R.id.ly_middle_classes)
    LinearLayout lyMiddleClasses;
    @Bind(R.id.tv_right_classes)
    TextView tvRightClasses;
    @Bind(R.id.img_right_classes)
    ImageView imgRightClasses;
    @Bind(R.id.ly_right_classes)
    LinearLayout lyRightClasses;
    @Bind(R.id.ly_class)
    LinearLayout lyClass;

    private XListView pullLoadMoreRecyclerView;
    private List<ClassesBean> mDatas = new ArrayList<>();
    private String ispage;
    private int nextpage = 1;
    private ClassesListAdapter trainListAdapter;

    private String costhight = "";//最高价格
    private String costlow = "";//最低价格
    private String flag = "";//课程属性标识，可不传 （从48接口获得）
    private String orderid = "1";//排序。1为综合排序，2为人气排序，  3.价格最低  4，价格最高

    private LeftSelectAdapter leftselectadapter;// 一级左侧筛选
    private List<LeftSelectBean> leftBeans = new ArrayList<>();// 一级左侧数据
    private PopupWindow popupLeft;
    private RecyclerView pd_pop1_leftlistview;//

    private String chooseId = "";//列表三级分类id
    private String keywords = "";
    private LinearLayout ly_all_class;
    private LinearLayout ly_all_school;
    private LinearLayout mlinerAll;
    private XListView xlist_all_school;
    private TextView mAllCourse, mAllSchool;

    private String allSchoolispage;
    private int allSchoolnextpage = 1;
    private List<TrainBean> mShoolList = new ArrayList<>();
    private TrainListAdapter schollListAdapter;
    private String refreshType = "1"; //判断课程刷新还是学校刷新  1 课程   学校

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        ButterKnife.bind(this);


        ly_all_class = (LinearLayout) findViewById(R.id.ly_all_class);
        ly_all_school = (LinearLayout) findViewById(R.id.ly_all_school);
        xlist_all_school = (XListView) findViewById(R.id.xlist_all_school);
        mAllCourse = (TextView) findViewById(R.id.mAllCourse);
        mAllSchool = (TextView) findViewById(R.id.mAllSchool);

        initLeftDate();
        getAllSchool();
        setTitle(getIntent().getStringExtra("title"));
        pullLoadMoreRecyclerView = (XListView) findViewById(R.id.pullLoadMoreRecyclerView);
        mlinerAll = (LinearLayout) findViewById(R.id.mlinerAll);
        pullLoadMoreRecyclerView.setPullLoadEnable(true);

        trainListAdapter = new ClassesListAdapter(cnt, mDatas);
        leftselectadapter = new LeftSelectAdapter(cnt, leftBeans);

        pullLoadMoreRecyclerView.setAdapter(trainListAdapter);
        pullLoadMoreRecyclerView.setXListViewListener(this);
        chooseId = getIntent().getStringExtra("id");
        if (getIntent().getStringExtra("keywords") == null || getIntent().getStringExtra("keywords").length() <= 0) {
            getBanner();
        } else {
            keywords = getIntent().getStringExtra("keywords");
            getBanner();
        }
        if (MyApplication.IS_SCHOOL_VISBLE.equals("0")) {  //判断学校列表是否显示
            ly_all_class.setVisibility(View.VISIBLE);
            ly_all_school.setVisibility(View.VISIBLE);
            mAllSchool.setVisibility(View.VISIBLE);
            mAllCourse.setVisibility(View.VISIBLE);
            mlinerAll.setVisibility(View.VISIBLE);

        } else {
            ly_all_class.setVisibility(View.VISIBLE);
            ly_all_school.setVisibility(View.GONE);
            mAllSchool.setVisibility(View.GONE);
            mAllCourse.setVisibility(View.GONE);
            mlinerAll.setVisibility(View.GONE);
        }
        mAllCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ly_all_class.setVisibility(View.VISIBLE);
                ly_all_school.setVisibility(View.GONE);
                refreshType = "1";
                mAllCourse.setTextColor(getResources().getColor(R.color.black));
                mAllSchool.setTextColor(getResources().getColor(R.color.text_hsitory));
            }
        });

        mAllSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ly_all_class.setVisibility(View.GONE);
                ly_all_school.setVisibility(View.VISIBLE);
                refreshType = "2";
                mAllSchool.setTextColor(getResources().getColor(R.color.text_hsitory));
                mAllSchool.setTextColor(getResources().getColor(R.color.black));
            }
        });

        schollListAdapter = new TrainListAdapter(cnt, mShoolList);
        xlist_all_school.setAdapter(schollListAdapter);
        xlist_all_school.setXListViewListener(this);
        xlist_all_school.setPullLoadEnable(true);
    }


    private void getBanner() {
        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    Logger.json(responseString);
                    if (jsonObject.optString("message").equals("1")) {
                        ispage = jsonObject.optString("isnextpage");
                        nextpage = jsonObject.optInt("nextpage");

                        if (ispage.equals("0")) {
                            XToast.warning("没有更多了");
                        }
                        JSONArray bannerjsonArray = jsonObject.optJSONArray("lessonlist");
                        for (int i = 0; i < bannerjsonArray.length(); i++) {
                            JSONObject object = bannerjsonArray.optJSONObject(i);
                            ClassesBean bannerBean = new ClassesBean();
                            bannerBean.id = object.optString("id");
                            bannerBean.title = object.optString("title");
                            bannerBean.applynum = object.optString("applynum");
                            bannerBean.cost = object.optString("cost");
                            bannerBean.picurl = object.optString("picurl");
                            bannerBean.flag = object.optString("flag");
                            bannerBean.times = object.optString("times");

                            JSONArray bannerjsonArray1 = object.optJSONArray("flaglist");
                            for (int j = 0; j < bannerjsonArray1.length(); j++) {
                                FlaglistBean flaglistbean = new FlaglistBean();
                                JSONObject object1 = bannerjsonArray1.optJSONObject(j);
                                flaglistbean.flagname = object1.optString("flagname");
                                bannerBean.ziCommentBeanList.add(flaglistbean);
                            }
                            mDatas.add(bannerBean);
                        }
                        trainListAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        ServerData.lessonbycourses(handler, keywords, chooseId, costhight, costlow, "", orderid, nextpage);
    }

    /**
     * 停止刷新，
     */
    private void onLoad() {
        if (refreshType.equals("1")) {
            pullLoadMoreRecyclerView.stopRefresh();
            pullLoadMoreRecyclerView.stopLoadMore();
            pullLoadMoreRecyclerView.setRefreshTime("刚刚");
        } else {
            xlist_all_school.stopRefresh();
            xlist_all_school.stopLoadMore();
            xlist_all_school.setRefreshTime("刚刚");
        }
    }

    @Override
    public void onRefresh() {

        if (refreshType.equals("1")) {

            nextpage = 1;
            trainListAdapter.notifyDataSetChanged();
            mDatas.clear();
            getBanner();
            onLoad();
        } else {
            schollListAdapter.notifyDataSetChanged();
            mShoolList.clear();
            allSchoolnextpage = 1;
            getAllSchool();
            onLoad();

        }
    }

    @Override
    public void onLoadMore() {
        if (refreshType.equals("1")) {
            if (ispage.equals("1")) {
                getBanner();
            } else if (ispage.equals("0")) {
                onLoad();
            }
        } else {

            if (allSchoolispage.equals("1")) {
                getAllSchool();
            } else if (allSchoolispage.equals("0")) {
                onLoad();
            }
        }

    }

    @OnClick({R.id.ly_left_classes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_left_classes:
                colorLeft();
                creatLefttpop();
                break;
        }
    }

    private void colorLeft() {
        tvLeftClasses.setTextColor(tvLeftClasses.getResources().getColor(R.color.colorPrimaryDark));
        tvMiddleClasses.setTextColor(tvMiddleClasses.getResources().getColor(R.color.pop_nomal));
        tvRightClasses.setTextColor(tvRightClasses.getResources().getColor(R.color.pop_nomal));

        imgLeftClasses.setImageResource(R.mipmap.xiala1);
        imgMiddleClasses.setImageResource(R.mipmap.xiala2);
        imgRightClasses.setImageResource(R.mipmap.xiala2);
    }


    private void creatLefttpop() {
        View popupWindow_view = getLayoutInflater().inflate(R.layout.pop_hotel_city, null, true);
        popupLeft = new PopupWindow(popupWindow_view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupLeft.setOutsideTouchable(false);
        popupLeft.setFocusable(true);
        // 半透明部分
        LinearLayout ap_pop1black = (LinearLayout) popupWindow_view.findViewById(R.id.ap_pop1black);
        ap_pop1black.getBackground().setAlpha(100);
        ap_pop1black.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                tvLeftClasses.setTextColor(tvLeftClasses.getResources().getColor(R.color.black));
                imgLeftClasses.setImageResource(R.mipmap.xiala1);
                popupLeft.dismiss();
                popupLeft = null;
            }
        });
        // 左侧列表
        pd_pop1_leftlistview = (RecyclerView) popupWindow_view.findViewById(R.id.pd_pop1_leftlistview);
        pd_pop1_leftlistview.setLayoutManager(new LinearLayoutManager(this));
        pd_pop1_leftlistview.addItemDecoration(new DividerDecoration(this));

        pd_pop1_leftlistview.setAdapter(leftselectadapter);
        leftselectadapter.setmOnItemClickListener(new LeftSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                mDatas.clear();
                trainListAdapter.notifyDataSetChanged();
                orderid = leftBeans.get(position).id;
                tvLeftClasses.setText(leftBeans.get(position).title);
                leftselectadapter.setSelection(position);
                leftselectadapter.notifyDataSetChanged();
                nextpage = 1;
                getBanner();
                popupLeft.dismiss();
            }
        });

        if (Build.VERSION.SDK_INT < 24) {//在7.0之前
            popupLeft.showAsDropDown(lyClass, 0, 0);//ll_rank是一个view,表示显示在下方
        } else {
// 适配 android 7.0
            int[] location = new int[2];
            lyClass.getLocationOnScreen(location);//re_hotel_address是一个view，表示显示在她的上方
            int x = location[0];
            int y = location[1];
            popupLeft.showAtLocation(lyClass, Gravity.NO_GRAVITY, 0, y);
        }
    }


    private void initLeftDate() {
        for (int i = 0; i < 4; i++) {
            LeftSelectBean chooseSelectBean = new LeftSelectBean();
            switch (i) {
                case 0:
                    chooseSelectBean.title = "综合排序";
                    chooseSelectBean.id = "1";
                    break;
                case 1:
                    chooseSelectBean.title = "人气排序";
                    chooseSelectBean.id = "2";
                    break;
                case 2:
                    chooseSelectBean.title = "价格最低";
                    chooseSelectBean.id = "3";
                    break;

                case 3:
                    chooseSelectBean.title = "价格最高";
                    chooseSelectBean.id = "4";
                    break;
                default:
                    break;
            }
            leftBeans.add(chooseSelectBean);
        }

    }

    /****
     * 获取全学校列表
     */
    private void getAllSchool() {
        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    Logger.json(responseString);
                    if (jsonObject.optString("message").equals("1")) {
                        allSchoolispage = jsonObject.optString("isnextpage");
                        allSchoolnextpage = jsonObject.optInt("nextpage");
                        if (allSchoolispage.equals("0")) {
                            XToast.warning("没有更多了");
                        }
                        JSONArray bannerjsonArray = jsonObject.optJSONArray("trainschool");
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
                            mShoolList.add(bannerBean);

                            if (!flag.equals("")) {
                                String[] strarray = flag.substring(1, flag.length() - 1).split(",");
                                for (int j = 0; j < strarray.length; j++) {
                                    String tag = strarray[j];
                                    try {
                                        String s2 = StringEscapeUtils.unescapeJava(tag.substring(1, tag.length() - 1));
                                        bannerBean.stringList.add(s2);
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }

                        Logger.d(mDatas.size());
                        trainListAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        ServerData.trainschool(handler, "", "", allSchoolnextpage);
    }


}

