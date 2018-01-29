package com.qianchang.togetherlesson.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2017/11/22 0022.
 */

public class SchoolPicBean {


    /**
     * message : 1
     * picar : [{"img":"/Uploads/20171122/list_2017112221175982706.jpg","info":"1"},{"img":"/Uploads/20171122/list_2017112221180040658.jpg","info":"2"},{"img":"/Uploads/20171122/list_2017112221180038135.jpg","info":"3"},{"img":"/Uploads/20171122/list_2017112221180056890.jpg","info":"4"}]
     */

    private String message;
    private List<PicarBean> picar;

    public static SchoolPicBean objectFromData(String str) {

        return new Gson().fromJson(str, SchoolPicBean.class);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PicarBean> getPicar() {
        return picar;
    }

    public void setPicar(List<PicarBean> picar) {
        this.picar = picar;
    }

    public static class PicarBean {
        /**
         * img : /Uploads/20171122/list_2017112221175982706.jpg
         * info : 1
         */

        private String img;
        private String info;

        public static PicarBean objectFromData(String str) {

            return new Gson().fromJson(str, PicarBean.class);
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
