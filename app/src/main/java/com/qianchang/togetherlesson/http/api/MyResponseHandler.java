/*
    Android Asynchronous Http Client
    Copyright (c) 2011 James Smith <james@loopj.com>
    http://loopj.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package com.qianchang.togetherlesson.http.api;

import android.content.Context;

import com.qianchang.togetherlesson.http.TextHttpResponseHandler;
import com.qianchang.togetherlesson.utils.Logger;
import com.qianchang.togetherlesson.utils.dialog.CustomProgressDialog;

import org.apache.http.Header;



public class MyResponseHandler extends TextHttpResponseHandler {

    private boolean showDialog;
    private Context mContext;
    protected CustomProgressDialog mProgressDialog;

    public MyResponseHandler(Context context, boolean showDialog) {
        this.mContext = context;
        this.showDialog = showDialog;
        mProgressDialog = new CustomProgressDialog(mContext);
    }

    public MyResponseHandler(Context context, boolean showDialog, String text) {
        this.mContext = context;
        this.showDialog = showDialog;
        mProgressDialog = new CustomProgressDialog(mContext);
    }

    public void showDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    public CustomProgressDialog getDialog() {
        return mProgressDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            if (mProgressDialog != null && mContext != null) {
                try {
                    mProgressDialog.show();
                    mProgressDialog.setMessage("请稍候...");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
    }

    @Override
    public void onFinish() {
        if (showDialog) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
        super.onFinish();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        // TODO Auto-generated method stub
        Logger.print1(responseString);
//        GetToast.useid(mContext, R.string.net_not_good);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        // TODO Auto-generated method stub
        Logger.print1("result=" + responseString);
    }

}
