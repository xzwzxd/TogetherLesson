package com.qianchang.togetherlesson.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * 作者：xiezhaowei
 * 时间：2017/11/28:11:28
 * 邮箱：1622955518@qq.com
 */
public class IndexBean {

    /**
     * message : 1
     * bannerlist : [{"id":"51","picurl":"/Uploads/20171108/pic_5a02826f89f9f.jpg","title":"商务英语"}]
     * courseslist : [{"id":"0","title":"全部","picurl":"/Uploads/20171023/pic_59ed6e8728d8f.png"},{"id":"61","title":"公务员","picurl":"/Uploads/20170816/pic_5993fa5512069.png"},{"id":"82","title":"2018考研","picurl":"/Uploads/20170816/pic_5993fb7f932fa.png"},{"id":"150","title":"雅思 托福 ","picurl":"/Uploads/20171120/pic_5a124cd3980f9.png"},{"id":"235","title":"C1驾驶证","picurl":"/Uploads/20170816/pic_5993fb4827dad.png"},{"id":"268","title":"UI设计","picurl":"/Uploads/20170919/pic_59c0aae350b6c.png"},{"id":"284","title":"产品经理","picurl":"/Uploads/20171013/pic_59e07e6d71060.png"},{"id":"290","title":"兴趣爱好","picurl":"/Uploads/20171023/pic_59ed5d4408e78.png"}]
     * newslist : [{"id":"41","title":"初级会计职称考试：2018初级会计师考试报名入口","hits":"154","collectnum":"3","author":"聚课小秘书","picarr":"/Uploads/20170607/list_2017060710001024493.jpg","picurl":"/Uploads/20171108/pic_5a02bbcfa3546.png","schoolid":"42"}]
     * indexpicurl : /Uploads/20171108/pic_5a02b46f6cf1d.jpg
     * indexpicurltitle : 开机公告页
     * indexpicurllink : http://139.196.105.136:91/index.php/Api/Vession/web?id=11
     * ismessage : 0
     * messagenum : 0
     */

    private String message;
    private String indexpicurl;
    private String indexpicurltitle;
    private String indexpicurllink;
    private String ismessage;
    private String messagenum;
    private List<BannerlistBean> bannerlist;
    private List<CourseslistBean> courseslist;
    private List<NewslistBean> newslist;

    public static IndexBean objectFromData(String str) {

        return new Gson().fromJson(str, IndexBean.class);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIndexpicurl() {
        return indexpicurl;
    }

    public void setIndexpicurl(String indexpicurl) {
        this.indexpicurl = indexpicurl;
    }

    public String getIndexpicurltitle() {
        return indexpicurltitle;
    }

    public void setIndexpicurltitle(String indexpicurltitle) {
        this.indexpicurltitle = indexpicurltitle;
    }

    public String getIndexpicurllink() {
        return indexpicurllink;
    }

    public void setIndexpicurllink(String indexpicurllink) {
        this.indexpicurllink = indexpicurllink;
    }

    public String getIsmessage() {
        return ismessage;
    }

    public void setIsmessage(String ismessage) {
        this.ismessage = ismessage;
    }

    public String getMessagenum() {
        return messagenum;
    }

    public void setMessagenum(String messagenum) {
        this.messagenum = messagenum;
    }

    public List<BannerlistBean> getBannerlist() {
        return bannerlist;
    }

    public void setBannerlist(List<BannerlistBean> bannerlist) {
        this.bannerlist = bannerlist;
    }

    public List<CourseslistBean> getCourseslist() {
        return courseslist;
    }

    public void setCourseslist(List<CourseslistBean> courseslist) {
        this.courseslist = courseslist;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class BannerlistBean {
        /**
         * id : 51
         * picurl : /Uploads/20171108/pic_5a02826f89f9f.jpg
         * title : 商务英语
         */

        private String id;
        private String picurl;
        private String title;

        public static BannerlistBean objectFromData(String str) {

            return new Gson().fromJson(str, BannerlistBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class CourseslistBean {
        /**
         * id : 0
         * title : 全部
         * picurl : /Uploads/20171023/pic_59ed6e8728d8f.png
         */

        private String id;
        private String title;
        private String picurl;

        public static CourseslistBean objectFromData(String str) {

            return new Gson().fromJson(str, CourseslistBean.class);
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
    }

    public static class NewslistBean {
        /**
         * id : 41
         * title : 初级会计职称考试：2018初级会计师考试报名入口
         * hits : 154
         * collectnum : 3
         * author : 聚课小秘书
         * picarr : /Uploads/20170607/list_2017060710001024493.jpg
         * picurl : /Uploads/20171108/pic_5a02bbcfa3546.png
         * schoolid : 42
         */

        private String id;
        private String title;
        private String hits;
        private String collectnum;
        private String author;
        private String picarr;
        private String picurl;
        private String schoolid;

        public static NewslistBean objectFromData(String str) {

            return new Gson().fromJson(str, NewslistBean.class);
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

        public String getHits() {
            return hits;
        }

        public void setHits(String hits) {
            this.hits = hits;
        }

        public String getCollectnum() {
            return collectnum;
        }

        public void setCollectnum(String collectnum) {
            this.collectnum = collectnum;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPicarr() {
            return picarr;
        }

        public void setPicarr(String picarr) {
            this.picarr = picarr;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public String getSchoolid() {
            return schoolid;
        }

        public void setSchoolid(String schoolid) {
            this.schoolid = schoolid;
        }
    }
}
