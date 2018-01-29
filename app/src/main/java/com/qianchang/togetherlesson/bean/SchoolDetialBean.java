package com.qianchang.togetherlesson.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * 作者：xiezhaowei
 * 时间：2018/1/10:19:51
 * 邮箱：1622955518@qq.com
 */
public class SchoolDetialBean {


    /**
     * message : 1
     * iscollect : 0
     * isapply : 0
     * trainschool : {"id":"29","title":"达内教育","picurl":"/Uploads/20171122/pic_5a15336b8f00c.jpg","collectnum":"78","flag":["能贷款","名师带班","就业分配"],"address":"哈尔滨哈西万达写字楼B栋三层","qq":"3162912837","qqqun":"207889516","phone":"13304645153 ","picurl2":"/Uploads/20171013/pic_59e01ec91165a.jpg"}
     */

    private String message;
    private String iscollect;
    private String isapply;
    private TrainschoolBean trainschool;

    public static SchoolDetialBean objectFromData(String str) {

        return new Gson().fromJson(str, SchoolDetialBean.class);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIscollect() {
        return iscollect;
    }

    public void setIscollect(String iscollect) {
        this.iscollect = iscollect;
    }

    public String getIsapply() {
        return isapply;
    }

    public void setIsapply(String isapply) {
        this.isapply = isapply;
    }

    public TrainschoolBean getTrainschool() {
        return trainschool;
    }

    public void setTrainschool(TrainschoolBean trainschool) {
        this.trainschool = trainschool;
    }

    public static class TrainschoolBean {
        /**
         * id : 29
         * title : 达内教育
         * picurl : /Uploads/20171122/pic_5a15336b8f00c.jpg
         * collectnum : 78
         * flag : ["能贷款","名师带班","就业分配"]
         * address : 哈尔滨哈西万达写字楼B栋三层
         * qq : 3162912837
         * qqqun : 207889516
         * phone : 13304645153
         * picurl2 : /Uploads/20171013/pic_59e01ec91165a.jpg
         */

        private String id;
        private String title;
        private String picurl;
        private String collectnum;
        private String address;
        private String qq;
        private String qqqun;
        private String phone;
        private String picurl2;
        private List<String> flag;

        public static TrainschoolBean objectFromData(String str) {

            return new Gson().fromJson(str, TrainschoolBean.class);
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

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public String getCollectnum() {
            return collectnum;
        }

        public void setCollectnum(String collectnum) {
            this.collectnum = collectnum;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getQqqun() {
            return qqqun;
        }

        public void setQqqun(String qqqun) {
            this.qqqun = qqqun;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPicurl2() {
            return picurl2;
        }

        public void setPicurl2(String picurl2) {
            this.picurl2 = picurl2;
        }

        public List<String> getFlag() {
            return flag;
        }

        public void setFlag(List<String> flag) {
            this.flag = flag;
        }
    }
}
