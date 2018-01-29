package com.qianchang.togetherlesson.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by HT on 2017/8/14.
 */

public class lessonlistBean {

    /**
     * message : 1
     * lessonlist : [{"id":"28","title":"VIP强化冲刺课程","applynum":"2","picurl":"/Uploads/20170802/pic_598140c85a6db.jpg","cost":"0","flag":"a,b,d","times":"5月10--15号","flaglist":[{"flagname":"通过率高"},{"flagname":"不过退款"},{"flagname":"收藏人多"}]},{"id":"39","title":"高端1v6系列","applynum":"0","picurl":"","cost":"36800","flag":"","times":"②6月15-22日","flaglist":[]},{"id":"40","title":"高端1v5系列","applynum":"0","picurl":"","cost":"16800","flag":"","times":"②6月15-22日","flaglist":[]},{"id":"41","title":"MAX通关经典","applynum":"0","picurl":"","cost":"15800","flag":"","times":"⑦6月13-20日 ⑧6月16-23日","flaglist":[]},{"id":"42","title":"MAX通关经典","applynum":"0","picurl":"","cost":"15800","flag":"","times":"⑦6月13-20日 ⑧6月16-23日","flaglist":[]},{"id":"43","title":"MAX通关经典","applynum":"0","picurl":"","cost":"5980","flag":"","times":"②6月16-22日","flaglist":[]},{"id":"44","title":"一对一私教VIP面试","applynum":"0","picurl":"","cost":"168000","flag":"","times":"②6月15-22日","flaglist":[]},{"id":"45","title":"一对一全程定制","applynum":"0","picurl":"","cost":"12800","flag":"","times":"⑦6月13-20日 ⑧6月16-23日","flaglist":[]},{"id":"46","title":"OAO课程系列","applynum":"0","picurl":"","cost":"17800","flag":"","times":"⑦6月13-20日 ⑧6月16-23日","flaglist":[]},{"id":"47","title":"在职在线专享","applynum":"0","picurl":"","cost":"7800","flag":"","times":"②6月15-22日","flaglist":[]},{"id":"48","title":"一对一全程定制","applynum":"0","picurl":"","cost":"15800","flag":"","times":"12小时","flaglist":[]},{"id":"49","title":"一对一量身定制","applynum":"0","picurl":"","cost":"980","flag":"","times":"6小时起约","flaglist":[]}]
     */

    private String message;
    private List<LessonlistBean> lessonlist;

    public static lessonlistBean objectFromData(String str) {

        return new Gson().fromJson(str, lessonlistBean.class);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LessonlistBean> getLessonlist() {
        return lessonlist;
    }

    public void setLessonlist(List<LessonlistBean> lessonlist) {
        this.lessonlist = lessonlist;
    }

    public static class LessonlistBean {
        /**
         * id : 28
         * title : VIP强化冲刺课程
         * applynum : 2
         * picurl : /Uploads/20170802/pic_598140c85a6db.jpg
         * cost : 0
         * flag : a,b,d
         * times : 5月10--15号
         * flaglist : [{"flagname":"通过率高"},{"flagname":"不过退款"},{"flagname":"收藏人多"}]
         */

        private String id;
        private String title;
        private String applynum;
        private String picurl;
        private String cost;
        private String flag;
        private String times;
        private List<FlaglistBean> flaglist;

        public static LessonlistBean objectFromData(String str) {

            return new Gson().fromJson(str, LessonlistBean.class);
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

        public String getApplynum() {
            return applynum;
        }

        public void setApplynum(String applynum) {
            this.applynum = applynum;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public List<FlaglistBean> getFlaglist() {
            return flaglist;
        }

        public void setFlaglist(List<FlaglistBean> flaglist) {
            this.flaglist = flaglist;
        }

        public static class FlaglistBean {
            /**
             * flagname : 通过率高
             */

            private String flagname;

            public static FlaglistBean objectFromData(String str) {

                return new Gson().fromJson(str, FlaglistBean.class);
            }

            public String getFlagname() {
                return flagname;
            }

            public void setFlagname(String flagname) {
                this.flagname = flagname;
            }
        }
    }
}
