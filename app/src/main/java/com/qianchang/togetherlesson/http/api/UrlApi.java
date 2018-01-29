/**
 * Project Name:FinancingP2p
 * File Name:UrlApi.java
 * Package Name:com.longcai.financingp2p.http.api
 * Date:2015-7-24涓嬪崍5:29:44
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 */

package com.qianchang.togetherlesson.http.api;


/**
 * @author Administrator
 * @ClassName:UrlApi
 * @Function: TODO
 * @Date: 2015-7-24 下午5:29:44
 * @since JDK 1.6
 */
public interface UrlApi {
    String ip = "http://139.196.105.136:91/index.php/Api/";
    String ip_pic = "http://139.196.105.136:91/Public"; //图片

    String REGISTER = "Login/regaction";//注册

    String RE_SMS = "Sendsmss/sendsmsreg";//注册发送短信接口：Sendsmss/sendsmsreg
    String SENDSMSS = "Sendsmss/sendsms1";//短信
    String GETLOGIN = "Login/loginaction?";//登录
    String FORGET_SMS = "Sendsmss/sendfindsms";// 短信找回密码
    String FINDPWDACTION = "Login/findpwdaction";// 通过短信，找回密码

    String BANNERS = "Home/index";//首页
    String RESETPASSWORD = "Login/resetpassword";// 重置密码

    String COMMUNITYLIST="Home/university"; //大学列表接口
    String lessonbyschool ="Home/lessonbyschool"; //大学列表接口
    String message ="Message/message"; //大学列表接口

    String ercode ="Home/ercode"; //大学列表接口
    String addsuggest ="Home/addsuggest"; //大学列表接口
    String usermessage ="Home/usermessage"; //大学列表接口
    String upavatare ="Login/upavatar"; //.上传头像

    String fragment_news ="Home/news"; //.上传头像
    String fragment_newsshow ="Home/newsshow"; //.上传头像
    String trainschool ="Home/trainschool"; //.培训机构列表
    String arealist ="home/arealist"; //.培训机构列表学校地区列表接口
    String trainschoolshow ="Home/trainschoolshow"; //.培训机构（学校）详情
    String LESSONBYSCHOOL ="Home/lessonbyschool"; //.培训机构（学校）详情
    String SCHOOLNEWS ="Home/schoolnews"; //.培训机构（学校）详情
    String LESSONSHOW ="Home/lessonshow"; //.培训机构（课程）详情
    String INTOCONSULT ="Home/intoconsult"; //.进入咨询页
    String addconsult ="Home/addconsult"; //.进入咨询页
    String APPLY ="Home/apply"; //..报名上课

    String LESSONAPPLY ="Home/lessonapply"; //课程.报名上课

    String CLUBLIST ="Club/Clublist"; //..报名上课
    String CLUBSHOW ="Club/Clubshow"; //..报名上课
    String MYCLUB ="Club/myClub"; //.我发布的话题列表接口
    String MYJOINCLUB ="Club/myjoinClub"; //我参与的话题列表接口
    String ADDCLUB ="Club/addclub"; //我参与的话题列表接口
    String EDITUSER ="Home/edituser"; //我参与的话题列表接口
    String COLLECTNEWS ="Collect/collectnews"; //收藏（添加和删除）新闻列表接口
    String COLLECTSCHOOL ="Collect/collectschool"; //.收藏（添加和删除）学校接口
    String MYCOLLECTSCHOOL ="Collect/mycollectschool"; //收藏学校列表接口
    String MYCOLLECTNEWS ="Collect/mycollectnews"; //收藏学校列表接口
    String ALLCOURSES ="Courses/allcourses"; //收藏学校列表接口


    String LESSONBYCOURSES ="Courses/lessonbycourses";     //根据分类id，查询课程：Courses/lessonbycourses 三级
    String FLAGLIST ="Courses/flaglist";     //.筛选，课程属性
    String GET_QQ ="Home/getschoolqq";     //.qq
    String ADDINFORM ="Club/addinform";     //.添加举报接口
    String LOGIN_WECHAT="Third/weixin";

    String addpinglun="Club/reply";
    String TRAINSCHOOLPHOTO="Home/trainschoolphoto";

    String NEWSTYPE="Home/newstype";

    String GETCOUPON="Home/getcoupon";
    String MYCOUPON="Home/mycoupon";


}
