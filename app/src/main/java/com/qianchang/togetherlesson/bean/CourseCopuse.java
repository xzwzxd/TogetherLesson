package com.qianchang.togetherlesson.bean;

import java.util.List;

/**
 * 作者：xiezhaowei
 * 时间：2017/11/27:14:05
 * 邮箱：1622955518@qq.com
 */
public class CourseCopuse {


    /**
     * message : 1
     * lesson : {"title":"室内设计","source":"保健副路6号","schoolid":"68","author":"","subject":"3D、Vray、CAD、PS","times":"随到随学","period":"60小课（45分钟）","cost":"2600","content":"http://139.196.105.136:91/index.php/Api/About/lessonweb?id=215","schooltitle":"","schoolpicurl":"/Uploads/20171127/pic_5a1b83d308499.png","isapply":"0","coupon":[{"id":"17","title":"100元优惠券"}],"activity":[{"id":"17","title":"100元优惠券"}]}
     * qq :
     * qqqun :
     * phone : 13945064293
     * picurl2 :
     */

    public String message;
    public LessonBean lesson;
    public String qq;
    public String qqqun;
    public String phone;
    public String picurl2;

   

    public static class LessonBean {
        /**
         * title : 室内设计
         * source : 保健副路6号
         * schoolid : 68
         * author :
         * subject : 3D、Vray、CAD、PS
         * times : 随到随学
         * period : 60小课（45分钟）
         * cost : 2600
         * content : http://139.196.105.136:91/index.php/Api/About/lessonweb?id=215
         * schooltitle :
         * schoolpicurl : /Uploads/20171127/pic_5a1b83d308499.png
         * isapply : 0
         * coupon : [{"id":"17","title":"100元优惠券"}]
         * activity : [{"id":"17","title":"100元优惠券"}]
         */

        public String title;
        public String source;
        public String schoolid;
        public String author;
        public String subject;
        public String times;
        public String period;
        public String cost;
        public String content;
        public String schooltitle;
        public String schoolpicurl;
        public String isapply;
        public List<CouponBean> coupon;
        public List<ActivityBean> activity;


        public static class CouponBean {
            /**
             * id : 17
             * title : 100元优惠券
             */

            public String id;
            public String title;

        }
        public static class ActivityBean {
            /**
             * id : 17
             * title : 100元优惠券
             */

            public String id;
            public String title;

        }
    }
}
