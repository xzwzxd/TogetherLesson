package com.qianchang.togetherlesson.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * 作者：xiezhaowei
 * 时间：2017/11/27:16:28
 * 邮箱：1622955518@qq.com
 */
public class MyComposeBean {


    /**
     * message : 1
     * alert : 成功
     * usercoupon : [{"id":"39","title":"每满500减150","couponid":"20","school":"测试号","lesson":"测试课程","endtime":"2017-11-29"}]
     */

    private String message;
    private String alert;
    private List<UsercouponBean> usercoupon;

    public static MyComposeBean objectFromData(String str) {

        return new Gson().fromJson(str, MyComposeBean.class);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public List<UsercouponBean> getUsercoupon() {
        return usercoupon;
    }

    public void setUsercoupon(List<UsercouponBean> usercoupon) {
        this.usercoupon = usercoupon;
    }

    public static class UsercouponBean {
        /**
         * id : 39
         * title : 每满500减150
         * couponid : 20
         * school : 测试号
         * lesson : 测试课程
         * endtime : 2017-11-29
         */

        private String id;
        private String title;
        private String couponid;
        private String school;
        private String lesson;
        private String endtime;

        public static UsercouponBean objectFromData(String str) {

            return new Gson().fromJson(str, UsercouponBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCouponid() {
            return couponid;
        }

        public void setCouponid(String couponid) {
            this.couponid = couponid;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getLesson() {
            return lesson;
        }

        public void setLesson(String lesson) {
            this.lesson = lesson;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }
    }
}
