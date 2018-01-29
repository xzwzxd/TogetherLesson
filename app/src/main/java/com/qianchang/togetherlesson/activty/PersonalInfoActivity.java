package com.qianchang.togetherlesson.activty;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.orhanobut.logger.Logger;
import com.qianchang.togetherlesson.R;
import com.qianchang.togetherlesson.app.MyApplication;
import com.qianchang.togetherlesson.base.BaseSwipeBackCompatActivity;
import com.qianchang.togetherlesson.http.api.MyResponseHandler;
import com.qianchang.togetherlesson.http.api.ServerData;
import com.qianchang.togetherlesson.http.api.UrlApi;
import com.qianchang.togetherlesson.http.json.JSONObjectEx;
import com.qianchang.togetherlesson.permisson.CropImageUtils;
import com.qianchang.togetherlesson.utils.AppTool;
import com.qianchang.togetherlesson.utils.GlideCircleTransform;
import com.qianchang.togetherlesson.utils.SelectPicPopupWindow;
import com.qianchang.togetherlesson.utils.SelectSexWindow;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.youth.xframe.widget.XToast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2016/10/11.
 */
public class PersonalInfoActivity extends BaseSwipeBackCompatActivity {


    //存储
    public static final int REQUEST_CODE_PERMISSION_STORAGE = 100;
    //相机
    public static final int REQUEST_CODE_PERMISSION_CAMERA = 101;
    @Bind(R.id.user_icon)
    ImageView userIcon;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.ly_name)
    LinearLayout lyName;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.ly_sex)
    LinearLayout lySex;
    @Bind(R.id.tv_school)
    TextView tvSchool;
    @Bind(R.id.ly_school)
    LinearLayout lySchool;
    @Bind(R.id.tv_class)
    TextView tvClass;
    @Bind(R.id.ly_class)
    LinearLayout lyClass;
    @Bind(R.id.user_phone)
    TextView userPhone;
    @Bind(R.id.l_user_icon)
    LinearLayout lUserIcon;
    private SelectPicPopupWindow menuWindow;
    private RequestManager glideRequest;
    private SelectSexWindow selectsexwindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        ButterKnife.bind(this);
        setTitle("个人资料");
        TextView textView = (TextView) findViewById(R.id.tv_right);
        textView.setText("保存");
        textView.setTextColor(getResources().getColor(R.color.colorWindowBg));
        textView.setVisibility(View.VISIBLE);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edituser();
            }
        });

        getLogin();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();

        tvName.setText(MyApplication.EDIT_NAME);
        tvSchool.setText(MyApplication.USER_SCHOOL);
        tvClass.setText(MyApplication.USER_CLASS);
    }

    @OnClick({R.id.ly_name, R.id.ly_sex, R.id.ly_school, R.id.ly_class, R.id.l_user_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_name:
                startActivity(new Intent(cnt, EditNameActivity.class));
                break;
            case R.id.ly_sex:
                selectsexwindow = new SelectSexWindow(PersonalInfoActivity.this, itemsOnSexClick);
                selectsexwindow.showAtLocation(lySex, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.ly_school:

                startActivity(new Intent(cnt, SchoolActivity.class));
                break;
            case R.id.ly_class:
                startActivity(new Intent(cnt, AgeActivity.class));
                break;

            case R.id.l_user_icon:
                menuWindow = new SelectPicPopupWindow(PersonalInfoActivity.this, itemsOnClick);
                menuWindow.showAtLocation(userIcon, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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
                    } else {
                        CropImageUtils.getInstance().takePhoto(PersonalInfoActivity.this);
                    }
                    break;
                case R.id.btn_pick_photo:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        CropImageUtils.getInstance().openAlbum(PersonalInfoActivity.this);
                    } else {
                        CropImageUtils.getInstance().openAlbum(PersonalInfoActivity.this);
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
                CropImageUtils.getInstance().cropPicture(PersonalInfoActivity.this, path);
            }

            @Override
            public void selectPictureFinish(String path) {
                //相册回调，去裁剪
                CropImageUtils.getInstance().cropPicture(PersonalInfoActivity.this, path);
            }

            @Override
            public void cropPictureFinish(String path) {
                glideRequest = Glide.with(cnt);
                glideRequest.load("file:///" + path).transform(new GlideCircleTransform(cnt)).into(userIcon);
                getUploadPic(path);
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
                    AndPermission.with(this).requestCode(REQUEST_CODE_PERMISSION_STORAGE).permission(Manifest.permission.WRITE_EXTERNAL_STORAGE).send();
                    break;
                //调用多个权限，相机和存储
                case REQUEST_CODE_PERMISSION_CAMERA:
                    //如果没有申请权限
                    if (!AndPermission.hasPermission(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        AndPermission.with(this).requestCode(REQUEST_CODE_PERMISSION_CAMERA).permission(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE).send();
                    } else {
                        CropImageUtils.getInstance().takePhoto(PersonalInfoActivity.this);
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
                        CropImageUtils.getInstance().takePhoto(PersonalInfoActivity.this);
                    }

                    @Override
                    public void onFailed(int requestCode, List<String> deniedPermissions) {
                        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
                        if (AndPermission.hasAlwaysDeniedPermission(PersonalInfoActivity.this, deniedPermissions)) {
                            AndPermission.defaultSettingDialog(PersonalInfoActivity.this, REQUEST_CODE_PERMISSION_CAMERA)
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

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.USER_SCHOOL.equals("")) {
            tvSchool.setText("请选择学校");
        } else {
            tvSchool.setText(MyApplication.USER_SCHOOL);
        }
        if (MyApplication.USER_CLASS.equals("")) {
            tvClass.setText("请选择年级");
        } else {
            tvClass.setText(MyApplication.USER_CLASS);
        }
    }

    private View.OnClickListener itemsOnSexClick = new View.OnClickListener() {

        public void onClick(View v) {
            selectsexwindow.dismiss();
            switch (v.getId()) {
                case R.id.select_man:
                    tvSex.setText("男");
                    MyApplication.SEX = "男";
                    break;
                case R.id.select_women:
                    tvSex.setText("女");
                    MyApplication.SEX = "女";
                    break;
                default:
                    break;
            }
        }
    };

    private void getLogin() {

        MyResponseHandler handler = new MyResponseHandler(this, true) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObjectEx jsonObject = new JSONObjectEx(responseString);
                    Logger.json(responseString);
                    if (jsonObject.opt("message").equals("1")) {

                        JSONObject jsonObject1 = new JSONObject(jsonObject.optString("usermessage"));
                        tvSex.setText(jsonObject1.optString("sex"));
                        tvName.setText(jsonObject1.optString("username"));
                        MyApplication.EDIT_NAME = jsonObject1.optString("username");
                        tvSchool.setText(jsonObject1.optString("school"));
                        MyApplication.USER_SCHOOL = jsonObject1.optString("school");
                        tvClass.setText(jsonObject1.optString("class"));
                        MyApplication.USER_CLASS = jsonObject1.optString("class");
                        userPhone.setText(jsonObject1.optString("code"));
                        glideRequest = Glide.with(cnt);
                        glideRequest.load(UrlApi.ip_pic + jsonObject1.optString("avatar")).transform(new GlideCircleTransform(cnt)).placeholder(R.mipmap.user_icon_defalt).into(userIcon);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.usermessage(handler, MyApplication.P2PPreferences.getUid());
    }

    private void getUploadPic(final String photoPath) {
        MyResponseHandler handler = new MyResponseHandler(cnt, false) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                try {
                    JSONObject jsonObject = new JSONObject(responseString);

                    if (jsonObject.optString("data").equals("1")) {
                        MyApplication.user_icon = jsonObject.optString("picurl");
                        glideRequest = Glide.with(cnt);
                        glideRequest.load("file://" + photoPath).transform(new GlideCircleTransform(cnt)).into(userIcon);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        ServerData.upavatar(handler, MyApplication.P2PPreferences.getUid(), AppTool.bitmapToString(photoPath));

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
        ServerData.edituser(handler, MyApplication.P2PPreferences.getUid(), MyApplication.SEX, MyApplication.EDIT_NAME, MyApplication.USER_SCHOOL, MyApplication.USER_CLASS);
    }

}
