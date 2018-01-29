package com.qianchang.togetherlesson.activty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.baseAdapter.PhotoAdapter;
import com.qianchang.togetherlesson.baseAdapter.RecyclerItemClickListener;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.qianchang.togetherlesson.utils.AppTool;
import com.youth.xframe.widget.XToast;

import org.apache.http.Header;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by admin on 2017/6/12.
 */

public class ToRelaseActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.add_mien_editText_news)
    EditText addMienEditTextNews;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    String name_str="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        ButterKnife.bind(this);
        setTitle("发布话题");
        TextView tv = (TextView) findViewById(R.id.tv_right);
        tv.setText("发送");
        tv.setTextColor(getResources().getColor(R.color.colorWindowBg));
        tv.setVisibility(View.VISIBLE);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addMienEditTextNews.getText().toString().trim().length()<1){
                    XToast.warning("请输入发布内容");
                }else {
                    addclub(addMienEditTextNews.getText().toString().trim(), name_str);
                }
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(PhotoAdapter.MAX)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(ToRelaseActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(ToRelaseActivity.this);
                        }
                    }
                }));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
             name_str = ""; //图片路径字符串的拼接 格式   a,b,c
            for (int i = 0; i < selectedPhotos.size(); i++) {
                name_str += AppTool.bitmapToString(selectedPhotos.get(i)) + ",";
            }
            name_str = name_str.substring(0, name_str.length() - 1);
        }
    }


    private void addclub(String content, String picarr) {

        MyResponseHandler handler = new MyResponseHandler(cnt, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    if (jsonObject.opt("message").equals("1")) {
                        XToast.success("发布成功");

                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.addclub(handler, MyApplication.P2PPreferences.getUid(), content, picarr);
    }
}
