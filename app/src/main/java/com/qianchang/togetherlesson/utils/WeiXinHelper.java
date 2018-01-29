package com.qianchang.togetherlesson.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by tx on 2016/8/19.
 */
public class WeiXinHelper {
    private IWXAPI mIWXAPI;
    private Activity mActivity;

    /**
     * 必须实例化后 使用微信功能。
     */
    public WeiXinHelper(Activity activity) {
        mActivity = activity;
        mIWXAPI = WXAPIFactory.createWXAPI(activity, "wx0dbf1d4dde22907d", true);
        mIWXAPI.registerApp("wx0dbf1d4dde22907d");
    }
    public void sendReqToFriends(String text) {
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = text;
        //构造一个req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());//唯一字段表示
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        mIWXAPI.sendReq(req);
    }

    public void sendReqToCircle(String text) {
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = text;
        //构造一个req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());//唯一字段表示
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        mIWXAPI.sendReq(req);
    }

    /**
     * 分享网页
     */

    public void sendPageToFriend(String url, String title, String description, boolean isSendFriend, int resourceId) {
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = url;

        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = title;
        msg.description = description;
        Bitmap thumb = BitmapFactory.decodeResource(mActivity.getResources(), resourceId);
        msg.thumbData = Bitmap2Bytes(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = isSendFriend ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        mIWXAPI.sendReq(req);

    }

    /**
     * todo
     * 分享app
     */


    public void sendPageToFCircle(String url, String title, String description, boolean isSendFriend, int resourceId) {
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = url;

        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = title;
        msg.description = description;
        Bitmap thumb = BitmapFactory.decodeResource(mActivity.getResources(), resourceId);
        msg.thumbData = Bitmap2Bytes(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = isSendFriend ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        mIWXAPI.sendReq(req);

    }

    private byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}
