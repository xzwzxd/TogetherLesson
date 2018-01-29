package com.qianchang.togetherlesson.activty;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.permisson.CropImageUtils;
import com.qianchang.togetherlesson.utils.GlideCircleTransform;
import com.qianchang.togetherlesson.utils.SelectPicPopupWindow;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.youth.xframe.widget.XToast;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/2/24.
 */

public class EditorPersonInfoActivity extends BaseSwipeBackCompatActivity  implements CompoundButton.OnCheckedChangeListener{
    @Bind(R.id.img_user_icon)
    ImageView imgUserIcon;
    @Bind(R.id.user_name)
    EditText userName;
    @Bind(R.id.user_school)
    RelativeLayout userSchool;
    @Bind(R.id.user_age)
    RelativeLayout userAge;
    @Bind(R.id.user_mam)
    RadioButton userMam;
    @Bind(R.id.user_women)
    RadioButton userWomen;
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.tv_school)
    TextView tvSchool;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.tv_age)
    TextView tvAge;

    private RequestManager glideRequest;

    //存储
    public static final int REQUEST_CODE_PERMISSION_STORAGE = 100;
    //相机
    public static final int REQUEST_CODE_PERMISSION_CAMERA = 101;
    private SelectPicPopupWindow menuWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person_info);
        ButterKnife.bind(this);
        setTitle("填写个人信息");

        glideRequest = Glide.with(cnt);
        userMam.setOnCheckedChangeListener(this);
        userWomen.setOnCheckedChangeListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.USER_SCHOOL.equals("")) {
            tvSchool.setText("请选择学校");
        } else {
            tvSchool.setText(MyApplication.USER_SCHOOL);
        }
        if (MyApplication.USER_CLASS.equals("")) {
            tvAge.setText("请选择年级");
        } else {
            tvAge.setText(MyApplication.USER_CLASS);
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.user_mam:
                MyApplication.SEX = "男";
                userMam.setChecked(true);
                userWomen.setChecked(false);
                break;
            case R.id.user_women:
                MyApplication.SEX = "女";
                userMam.setChecked(false);
                userWomen.setChecked(true);
                break;
        }

    }
    @OnClick({R.id.user_school, R.id.user_age, R.id.login_btn, R.id.img_user_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_school:
                startActivity(new Intent(cnt, SchoolActivity.class));
                break;
            case R.id.user_age:
                startActivity(new Intent(cnt, AgeActivity.class));
                break;
            case R.id.login_btn:
//                if ( MyApplication.P2PPreferences.getUid().equals("")){
//
//                    XToast.warning("请注册后再操作");
//                }else{
//                }
                edituser();
                break;
            case R.id.img_user_icon:
                menuWindow = new SelectPicPopupWindow(EditorPersonInfoActivity.this, itemsOnClick);
                menuWindow.showAtLocation(imgUserIcon, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            default:
                break;
        }
    }
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        checkPermission(REQUEST_CODE_PERMISSION_CAMERA);
                    }  else {
                        CropImageUtils.getInstance().takePhoto(EditorPersonInfoActivity.this);
                    }
                    break;
                case R.id.btn_pick_photo:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        CropImageUtils.getInstance().openAlbum(EditorPersonInfoActivity.this);
                    } else  {
                        CropImageUtils.getInstance().openAlbum(EditorPersonInfoActivity.this);
                    }
                    break;
                default:
                    break;
            }
        }

    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        CropImageUtils.getInstance().onActivityResult(this, requestCode, resultCode, data, new CropImageUtils.OnResultListener() {
            @Override
            public void takePhotoFinish(String path) {
                //拍照回调，去裁剪
                CropImageUtils.getInstance().cropPicture(EditorPersonInfoActivity.this, path);
            }

            @Override
            public void selectPictureFinish(String path) {
                //相册回调，去裁剪
                CropImageUtils.getInstance().cropPicture(EditorPersonInfoActivity.this, path);
            }

            @Override
            public void cropPictureFinish(String path) {
//                ToastUtils.showLongToast("裁剪保存在：" + path);
                //裁剪回调
                glideRequest.load("file:///" + path).transform(new GlideCircleTransform(cnt)).into(imgUserIcon);

            }
        });

    }

    /**
     * 检测权限
     */
    public void checkPermission(int permissionType) {
        if (Build.VERSION.SDK_INT >= 23) {
            switch (permissionType) {
                //调用单个权限
                case REQUEST_CODE_PERMISSION_STORAGE:
                    AndPermission.with(this)
                            .requestCode(REQUEST_CODE_PERMISSION_STORAGE)
                            .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .send();
                    break;
                //调用多个权限，相机和存储
                case REQUEST_CODE_PERMISSION_CAMERA:
                    //如果没有申请权限
                    if (!AndPermission.hasPermission(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        AndPermission.with(this)
                                .requestCode(REQUEST_CODE_PERMISSION_CAMERA)
                                .permission(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                                .send();
                    } else {
                        CropImageUtils.getInstance().takePhoto(EditorPersonInfoActivity.this);
                    }
                    break;
            }
        }
    }

    /**
     * 动态权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_STORAGE:
                //就用直接一句话搞定
                AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
                break;
            case REQUEST_CODE_PERMISSION_CAMERA:
                //自定义Dialog
                AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, List<String> grantPermissions) {
                        CropImageUtils.getInstance().takePhoto(EditorPersonInfoActivity.this);
                    }

                    @Override
                    public void onFailed(int requestCode, List<String> deniedPermissions) {
                        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
                        if (AndPermission.hasAlwaysDeniedPermission(EditorPersonInfoActivity.this, deniedPermissions)) {
                            AndPermission.defaultSettingDialog(EditorPersonInfoActivity.this, REQUEST_CODE_PERMISSION_CAMERA)
                                    .setTitle(getString(R.string.permission_request_error))
                                    .setMessage(getString(R.string.permission_avatar))
                                    .setNegativeButton(getString(R.string.cancel_value), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                    .show();
                        }
                    }
                });
                break;
        }
    }

    private void edituser() {
        MyResponseHandler handler = new MyResponseHandler(cnt, false) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.optString("message").equals("1")) {
                        XToast.success("修改成功");
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.edituser(handler, MyApplication.P2PPreferences.getUid(), MyApplication.SEX, userName.getText().toString().trim(), MyApplication.USER_SCHOOL, MyApplication.USER_CLASS);
    }

}
