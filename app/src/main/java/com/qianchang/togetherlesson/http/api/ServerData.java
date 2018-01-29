package com.qianchang.togetherlesson.http.api;


import com.qianchang.togetherlesson.http.AsyncHttpClient;
import com.qianchang.togetherlesson.http.RequestParams;

public class ServerData implements UrlApi {

    /****
     * 测试
     */
    public static void test(MyResponseHandler handler, String param) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("param", param);
        client.post("http://192.168.9.103/index.php/Api/Test", parameter, handler);
    }


    /****
     * 注册短信
     */

    public static void Test(MyResponseHandler handler, String action, String data) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("action", action);
        parameter.put("data", data);

        client.post(UrlApi.ip + RE_SMS, parameter, handler);
    }


    /****
     * 注册短信
     */

    public static void re_sms(MyResponseHandler handler, String mobile) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("mobile", mobile);
        client.post(UrlApi.ip + RE_SMS, parameter, handler);
    }

    /*****
     * 注册
     *
     * @param handler
     */
    public static void getregister(MyResponseHandler handler, String mobile, String password, String code) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("mobile", mobile);
        parameter.put("password", password);
        parameter.put("code", code);

        client.get(UrlApi.ip + REGISTER, parameter, handler);
    }


    /****
     * 登录
     *
     * @param handler
     * @param mobile
     * @param password
     */
    public static void getLogin(MyResponseHandler handler, String mobile, String password) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("mobile", mobile);
        parameter.put("password", password);
        client.get(UrlApi.ip + GETLOGIN, parameter, handler);
    }

    /****
     * 短信  （忘记密码）
     *
     * @param handler
     * @param mobile
     */
    public static void forget_sms(MyResponseHandler handler, String mobile) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("mobile", mobile);
        client.post(UrlApi.ip + FORGET_SMS, parameter, handler);
    }


    /****
     * banner
     *
     * @param handler
     * @param
     */
    public static void get_banner(MyResponseHandler handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();

        client.post(UrlApi.ip + BANNERS, parameter, handler);
    }

    public static void communitylist(MyResponseHandler handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        client.post(UrlApi.ip + COMMUNITYLIST, parameter, handler);
    }


    /***
     *  学校列表
     *
     * @param handler
     */
    public static void essonbyschool(MyResponseHandler handler, String trainschoolid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("trainschoolid", trainschoolid);
        client.post(UrlApi.ip + lessonbyschool, parameter, handler);
    }


    /***
     *
     *  消息列表接口：
     * @param handler
     */
    public static void message(MyResponseHandler handler, String uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        client.post(UrlApi.ip + message, parameter, handler);
    }

    /***
     *
     *  二维码
     * @param handler
     */
    public static void ercode(MyResponseHandler handler, String uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        client.post(UrlApi.ip + ercode, parameter, handler);
    }

    /***
     *
     *  添加意见反馈接口
     * @param handler
     */
    public static void addsuggest(MyResponseHandler handler, String uid, String content) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        parameter.put("content", content);
        client.post(UrlApi.ip + addsuggest, parameter, handler);
    }

    /***
     *
     * 用户信息接口
     * @param handler
     */
    public static void usermessage(MyResponseHandler handler, String uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        client.post(UrlApi.ip + usermessage, parameter, handler);
    }

    /***
     *
     * 用户信息接口
     * @param handler
     */
    public static void upavatar(MyResponseHandler handler, String uid, String avatar) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        parameter.put("avatar", avatar);

        client.post(UrlApi.ip + upavatare, parameter, handler);
    }


    /***
     *
     * 资讯列表接口：
     * @param handler
     */
    public static void fragment_news(MyResponseHandler handler, String indextype, String paga) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("indextype", indextype);
        parameter.put("paga", paga);
        client.post(UrlApi.ip + fragment_news, parameter, handler);
    }

    /***
     *
     * 资讯（小百科和精选课程）详情接口
     * @param handler
     */
    public static void fragment_newsshow(MyResponseHandler handler, String newsid, String uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("newsid", newsid);
        parameter.put("uid", uid);
        client.post(UrlApi.ip + fragment_newsshow, parameter, handler);
    }

    /***
     *
     * 培训机构列表
     * @param handler
     */
    public static void trainschool(MyResponseHandler handler, String typeid, String areaid, int page) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("typeid", typeid);
        parameter.put("areaid", areaid);
        parameter.put("page", page);
        client.post(UrlApi.ip + trainschool, parameter, handler);
    }


    /***
     *
     * 培训机构列表
     * @param handler
     */
    public static void arealist(MyResponseHandler handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        client.post(UrlApi.ip + arealist, parameter, handler);
    }

    /***
     *
     * 培训机构列表详情
     * @param handler
     */
    public static void trainschoolshow(MyResponseHandler handler, String schoolid, String uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("schoolid", schoolid);
        parameter.put("uid", uid);
        client.get(UrlApi.ip + trainschoolshow, parameter, handler);
    }

    /***
     *
     *
     13。根据培训机构查课程列表
     * @param handler
     */
    public static void lessonbyschool(MyResponseHandler handler, String trainschoolid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("trainschoolid", trainschoolid);
        client.post(UrlApi.ip + LESSONBYSCHOOL, parameter, handler);
    }


    /***
     *
     *
     13。根据培训机构查课程列表
     * @param handler
     */
    public static void schoolnews(MyResponseHandler handler, String schoolid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("schoolid", schoolid);
        client.post(UrlApi.ip + SCHOOLNEWS, parameter, handler);
    }

    /***
     *
     *
     13。根据培训机构查课程列表
     * @param handler
     */
    public static void lessonshow(MyResponseHandler handler, String lessonid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("lessonid", lessonid);
        client.post(UrlApi.ip + LESSONSHOW, parameter, handler);
    }

    /***
     *
     * 进入咨询页
     * @param handler
     */
    public static void intoconsult(MyResponseHandler handler, String schoolid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("schoolid", schoolid);
        client.post(UrlApi.ip + INTOCONSULT, parameter, handler);
    }

    /***
     *
     * 添加咨询
     * @param handler
     */
    public static void addconsult(MyResponseHandler handler, String schoolid, String uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("schoolid", schoolid);
        parameter.put("uid", uid);
        client.post(UrlApi.ip + addconsult, parameter, handler);
    }


    /***
     *
     * 添加咨询
     * @param handler
     */
    public static void apply(MyResponseHandler handler, String schoolid, String uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("schoolid", schoolid);
        parameter.put("uid", uid);
        client.post(UrlApi.ip + APPLY, parameter, handler);
    }

    /***
     *
     * 课程添加咨询
     * @param handler
     */
    public static void lessonapply(MyResponseHandler handler, String lessonid, String uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("lessonid", lessonid);
        parameter.put("uid", uid);
        client.post(UrlApi.ip + LESSONAPPLY, parameter, handler);
    }

    /***
     *
     * 报名上课
     * @param handler
     */
    public static void clublist(MyResponseHandler handler, int page) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("page", page);
        client.post(UrlApi.ip + CLUBLIST, parameter, handler);
    }

    /***
     *
     * ..话题详情接口
     * @param handler
     */
    public static void clubshow(MyResponseHandler handler, String cid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("cid", cid);
        client.post(UrlApi.ip + CLUBSHOW, parameter, handler);
    }

    /***
     *
     19.我发布的话题列表接口
     * @param handler
     */
    public static void myClub(MyResponseHandler handler, String uid, int page) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        parameter.put("page", page);
        client.post(UrlApi.ip + MYCLUB, parameter, handler);
    }

    /***
     *
     * .我参与的话题列表接口
     * @param handler
     */
    public static void myjoinClub(MyResponseHandler handler, String uid, int page) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        parameter.put("page", page);
        client.post(UrlApi.ip + MYJOINCLUB, parameter, handler);
    }

    /***
     *
     * .发布话题接口
     * @param handler
     */
    public static void addclub(MyResponseHandler handler, String uid, String content, String picarr) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        parameter.put("content", content);
        parameter.put("picarr", picarr);
        client.post(UrlApi.ip + ADDCLUB, parameter, handler);
    }

    /***
     *
     * ..修改用户信息接口
     * @param handler
     */
    public static void edituser(MyResponseHandler handler, String uid, String sex, String username, String school, String class2) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        parameter.put("sex", sex);
        parameter.put("username", username);
        parameter.put("school", school);
        parameter.put("class2", class2);
        client.post(UrlApi.ip + EDITUSER, parameter, handler);
    }

    /***
     *
     * 收藏的新问接,
     * @param handler
     */
    public static void collectnews(MyResponseHandler handler, String uid, String nid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        parameter.put("nid", nid);
        client.post(UrlApi.ip + COLLECTNEWS, parameter, handler);
    }

    /***
     *
     * .收藏的学校列表接口
     * @param handler
     */
    public static void collectschool(MyResponseHandler handler, String uid, String sid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        parameter.put("sid", sid);
        client.post(UrlApi.ip + COLLECTSCHOOL, parameter, handler);
    }


    /***
     *
     * 收藏的xuexiao   Lie表
     * @param handler
     */
    public static void mycollectschool(MyResponseHandler handler, String uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        client.post(UrlApi.ip + MYCOLLECTSCHOOL, parameter, handler);
    }

    /***
     *
     * .收藏的学校列表接口
     * @param handler
     */
    public static void mycollectnews(MyResponseHandler handler, String uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        client.post(UrlApi.ip + MYCOLLECTNEWS, parameter, handler);
    }


    /***
     *
     * .所有课程分类
     * @param handler
     */
    public static void allcourses(MyResponseHandler handler, String coursesid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("coursesid", coursesid);
        client.post(UrlApi.ip + ALLCOURSES, parameter, handler);
    }


    /***
     *  coursesid     //分类id ,不传为查询所有
     costhight    //最高价格，可不传
     costlow       //最低价格，可不传
     flag            //课程属性标识，可不传 （从48接口获得）
     orderid      //排序。1为综合排序，2为人气排序，  3.价格最低  4，价格最高
     page        //页码

     *
     * @param handler
     */
    public static void lessonbycourses(MyResponseHandler handler, String keywords, String coursesid, String costhight,
                                       String costlow, String flag, String orderid, int page) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("keywords", keywords);
        parameter.put("coursesid", coursesid);
        parameter.put("costhight", costhight);
        parameter.put("costlow", costlow);
        parameter.put("flag", flag);
        parameter.put("orderid", orderid);
        parameter.put("page", page);
        client.post(UrlApi.ip + LESSONBYCOURSES, parameter, handler);
    }

    /*****
     *  筛选属性列表
     * @param handler
     */
    public static void flaglist(MyResponseHandler handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        client.post(UrlApi.ip + FLAGLIST, parameter, handler);
    }

    /*****
     *  获取qq号  qq群
     * @param handler
     */
    public static void get_qq(MyResponseHandler handler, String schoolid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("schoolid", schoolid);
        client.post(UrlApi.ip + GET_QQ, parameter, handler);
    }

    /*****
     *  通过原密码
     * @param handler
     */
    public static void resetpassword(MyResponseHandler handler, String uid, String password, String oldpassword) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        parameter.put("password", password);
        parameter.put("oldpassword", oldpassword);
        client.post(UrlApi.ip + RESETPASSWORD, parameter, handler);
    }

    /*****
     *  添加举报接口
     * @param handler
     */
    public static void addinform(MyResponseHandler handler, String clubid, String uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        parameter.put("clubid", clubid);
        client.post(UrlApi.ip + ADDINFORM, parameter, handler);
    }

    /****
     * .通过短信，找回密码
     *
     * @param handler
     * @param mobile
     * @param password
     */
    public static void findpwdaction(MyResponseHandler handler, String mobile, String password) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("mobile", mobile);
        parameter.put("password", password);
        client.post(ip + FINDPWDACTION, parameter, handler);
    }

    public static void login_wechat(MyResponseHandler handler, String openid, String token) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("openid", openid);
        parameter.put("token", token);
        client.post(ip + LOGIN_WECHAT, parameter, handler);
    }


    public static void addpinglun(MyResponseHandler handler, String cid, String uid, String content) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("cid", cid);
        parameter.put("uid", uid);
        parameter.put("content", content);
        client.post(ip + addpinglun, parameter, handler);
    }


    public static void trainschoolphoto(MyResponseHandler handler, String schoolid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("schoolid", schoolid);
        client.post(ip + TRAINSCHOOLPHOTO, parameter, handler);
    }

    public static void newstype(MyResponseHandler handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        client.post(ip + NEWSTYPE, parameter, handler);
    }

    /****
     *
     * 领取优惠券
     * @param handler
     */
    public static void getcoupon(MyResponseHandler handler, String uid, String couponid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        parameter.put("couponid", couponid);
        client.post(ip + GETCOUPON, parameter, handler);
    }

    /****
     *
     * 我的优惠券
     * @param handler
     */
    public static void mycoupon(MyResponseHandler handler, String uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams parameter = new RequestParams();
        parameter.put("uid", uid);
        client.post(ip + MYCOUPON, parameter, handler);
    }
}
