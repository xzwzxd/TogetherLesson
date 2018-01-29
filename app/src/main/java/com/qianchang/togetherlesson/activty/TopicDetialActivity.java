package com.qianchang.togetherlesson.activty;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.baseAdapter.NeIghbourHoodDetialPicAdapter;
import com.qianchang.togetherlesson.baseAdapter.NeighbourhoodDetialPingjaiAdapter;
import com.qianchang.togetherlesson.bean.PinglunBean;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.api.UrlApi;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.qianchang.togetherlesson.pull.PullToRefreshBase;
import com.qianchang.togetherlesson.pull.PullToRefreshListView;
import com.qianchang.togetherlesson.utils.GetToast;
import com.qianchang.togetherlesson.utils.GlideCircleTransform;
import com.qianchang.togetherlesson.utils.IntentUtils;
import com.qianchang.togetherlesson.utils.MyGridView;
import com.qianchang.togetherlesson.utils.RoundedImageView;
import com.qianchang.togetherlesson.utils.SharePopWindow;
import com.qianchang.togetherlesson.utils.WeiXinHelper;
import com.youth.xframe.widget.XToast;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.carbs.android.library.MDDialog;

/**
 * Created by admin on 2017/6/10.
 */

public class TopicDetialActivity extends BaseSwipeBackCompatActivity {
    private WeiXinHelper weiXinHelper;
    private SharePopWindow sharePopWindow;

    public List<String> stringList = new ArrayList<>();  //存储图片结合
    private RequestManager glideRequest;
    ;
    //  --------------------HeaderView----------------------------------------  /
    private RoundedImageView item_img_find;
    private TextView item_tv_name;
    private TextView item_tv_time;
    private TextView tv_neighbourhood_content;
    private TextView item_tv_comment_num;
    private MyGridView mygrid_beighbourhood;
    private ImageView img_comment;
    private ListView mylistbeighbourhood_pinnglun;
    private String str_picarr;
    private NeIghbourHoodDetialPicAdapter neighbourhoodadapter;
    private NeighbourhoodDetialPingjaiAdapter neighbourhooddetialpingjaiadapter;

    private String ispage="";
    private String nextpage = "1";

    private PullToRefreshListView list_fragment_active;
    private List<PinglunBean> pinglunList = new ArrayList<>();
    private String isdian, isping;

    private LinearLayout pagefragment_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detial);
        setTitle("话题详情");
        findViewById(R.id.tv_right).setBackgroundResource(R.mipmap.img_more);
        setRightButtonVisibility(View.VISIBLE);
        findViewById(R.id.tv_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sharePopWindow == null) {
                    sharePopWindow = new SharePopWindow(cnt, itemClickListener, "0");
                }
                if (!sharePopWindow.isShowing()) {
                    sharePopWindow.showAtLocation(findViewById(R.id.tv_right), Gravity.BOTTOM, 0, 0);
                }
            }
        });

        weiXinHelper = new WeiXinHelper(TopicDetialActivity.this);
        glideRequest= Glide.with(this);
        mylistbeighbourhood_pinnglun = (ListView) findViewById(R.id.mylistbeighbourhood_pinnglun);
        list_fragment_active = (PullToRefreshListView) findViewById(R.id.list_fragment_active);
        list_fragment_active.setPullLoadEnabled(false);
        list_fragment_active.setScrollLoadEnabled(true);
        list_fragment_active.setVerticalScrollBarEnabled(true);
        mylistbeighbourhood_pinnglun = list_fragment_active.getRefreshableView();

        View view = LayoutInflater.from(cnt).inflate(R.layout.topic_detial_headerview, null);
        item_tv_comment_num = (TextView) view.findViewById(R.id.item_tv_comment_num);
        item_img_find = (RoundedImageView) view.findViewById(R.id.item_img_find);
        item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
        item_tv_time = (TextView) view.findViewById(R.id.item_tv_time);
        tv_neighbourhood_content = (TextView) view.findViewById(R.id.tv_neighbourhood_content);
        mygrid_beighbourhood = (MyGridView) view.findViewById(R.id.mygrid_beighbourhood);
        img_comment = (ImageView) view.findViewById(R.id.img_comment);

        mylistbeighbourhood_pinnglun.addHeaderView(view);


        neighbourhoodadapter = new NeIghbourHoodDetialPicAdapter(cnt, stringList);
        mygrid_beighbourhood.setAdapter(neighbourhoodadapter);

        /****
         *  评价
         */

        neighbourhooddetialpingjaiadapter = new NeighbourhoodDetialPingjaiAdapter(cnt, pinglunList);
        mylistbeighbourhood_pinnglun.setAdapter(neighbourhooddetialpingjaiadapter);

        img_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MDDialog.Builder(cnt)
                        .setContentView(R.layout.comment_content_dialog)
                        .setContentViewOperator(new MDDialog.ContentViewOperator() {
                            @Override
                            public void operate(View contentView) {
                                EditText et = (EditText) contentView.findViewById(R.id.edit0);
                                et.setHint("请填写您的评论");
                            }
                        })
                        .setTitle("评论")
                        .setPositiveButtonMultiListener(new MDDialog.OnMultiClickListener() {
                            @Override
                            public void onClick(View clickedView, View contentView) {
                                EditText et = (EditText) contentView.findViewById(R.id.edit0);
                                if (!et.getText().toString().trim().equals("")) {

                                    MyResponseHandler handler = new MyResponseHandler(cnt, true) {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                            super.onSuccess(statusCode, headers, responseString);
                                            try {
                                                JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                                                if (jsonObject.opt("message").equals("1")) {
                                                    GetToast.useString(cnt, "评论成功");
                                                    img_comment.setBackgroundResource(R.mipmap.is_comment);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    ServerData.addpinglun(handler, getIntent().getStringExtra("id"), MyApplication.P2PPreferences.getUid(), et.getText().toString().trim());
                                }
                            }
                        })
                        .setWidthMaxDp(600).setShowButtons(true).create().show();
            }
        });


        list_fragment_active.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });


        mygrid_beighbourhood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ArrayList<String> arrayList = new ArrayList<>();
                for (int j = 0; j < stringList.size(); j++) {
                    arrayList.add(stringList.get(j));
                }
                IntentUtils.startToImageShow(cnt, arrayList, i);
            }
        });
        getBanner();

    }

    private void getBanner() {
        MyResponseHandler handler = new MyResponseHandler(cnt, false) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    Logger.json(responseString);
                    if (jsonObject.optString("message").equals("1")) {
                        JSONObjectEx jsonObject1 = new JSONObjectEx(jsonObject.optString("data"));
                        jsonObject1.optString("id");
                        jsonObject1.optString("uid");
                        jsonObject1.optString("posttime");
                        jsonObject1.optString("picarr");
                        jsonObject1.optString("content");
                        jsonObject1.optString("username");
                        jsonObject1.optString("avatar");

                        String str_marks = jsonObject1.optString("picarr");
                        if (!str_marks.equals("")) {
                            String[] strarray = str_marks.substring(1, str_marks.length() - 1).split(",");
                            for (int j = 0; j < strarray.length; j++) {
                                String tag = strarray[j];
                                try {
                                    String s2 = StringEscapeUtils.unescapeJava(tag.substring(1, tag.length() - 1));
                                    stringList.add(s2);
                                } catch (Exception e) {
                                }
                            }
                        }

                        glideRequest.load(UrlApi.ip_pic + jsonObject1.optString("avatar")).placeholder(R.mipmap.zhanweitu5).transform(new GlideCircleTransform(cnt)).into(item_img_find);
                        item_tv_name.setText(jsonObject1.optString("username"));
                        item_tv_time.setText(jsonObject1.optString("posttime"));
                        tv_neighbourhood_content.setText(jsonObject1.optString("content"));
                        JSONArray bannerjsonArray = jsonObject1.optJSONArray("pinglunnum");

                        for (int i = 0; i < bannerjsonArray.length(); i++) {
                            JSONObject object = bannerjsonArray.optJSONObject(i);
                            PinglunBean bannerBean = new PinglunBean();
                            bannerBean.id = object.optString("id");
                            bannerBean.uid = object.optString("uid");
                            bannerBean.content = object.optString("content");
                            bannerBean.posttime = object.optString("posttime");
                            bannerBean.hid = object.optString("hid");
                            bannerBean.type = object.optString("type");
                            bannerBean.username = object.optString("username");
                            bannerBean.avatar = object.optString("avatar");
                            bannerBean.rename = object.optString("rename");
                            pinglunList.add(bannerBean);
                        }
                        item_tv_comment_num.setText(pinglunList.size()+"");
                        neighbourhoodadapter.notifyDataSetChanged();
                        neighbourhooddetialpingjaiadapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.clubshow(handler, getIntent().getStringExtra("id"));
    }

//
    /***
     *  添加举报接口
     */
    private void addinform() {

        MyResponseHandler handler = new MyResponseHandler(this, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);

                    if (jsonObject.opt("message").equals("1")) {
                        XToast.success("举报已提交管理员会重新审核该内容");
                    } else {
                        XToast.success("举报失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.addinform(handler, getIntent().getStringExtra("id"), MyApplication.P2PPreferences.getUid());
    }

    private View.OnClickListener itemClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.wechat:
                    weiXinHelper.sendPageToFriend("http://115.28.215.113:83/index.php/Api/Danye/share", "聚客", "", true, R.mipmap.ic_logo);
                    sharePopWindow.dismiss();
                    break;
                case R.id.circle:
                    weiXinHelper.sendPageToFCircle("http://115.28.215.113:83/index.php/Api/Danye/share", "聚客", "", false, R.mipmap.ic_logo);
                    sharePopWindow.dismiss();

                    break;
                case R.id.to_report: //举报
                    addinform();
                    sharePopWindow.dismiss();

                    break;
            }
        }
    };

}
