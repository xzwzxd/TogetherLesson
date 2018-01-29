package com.qianchang.togetherlesson.activty;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.youth.xframe.widget.XToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/6/15.
 */

public class EditNameActivity extends BaseSwipeBackCompatActivity {
    @Bind(R.id.ed_coman)
    EditText edComan;
    @Bind(R.id.btn_sure)
    Button btnSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coman);
        ButterKnife.bind(this);
        setTitle("修改昵称");
    }

    @OnClick(R.id.btn_sure)
    public void onViewClicked() {

        if (!edComan.getText().toString().trim().equals("")){
            MyApplication.EDIT_NAME=edComan.getText().toString().trim();
            finish();
        }else{
            XToast.warning("请填写名字");
        }
    }
}
