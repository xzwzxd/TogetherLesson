package com.qianchang.togetherlesson.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by admin on 2017/7/23.
 */

public class AllClassesBean {


    /**
     * message : 1
     * alllist : [{"id":"56","title":"外语","parentid":"0","son":[{"id":"57","title":"小语种","parentid":"56","son":[{"id":"59","title":"梵语","parentid":"57"},{"id":"60","title":"印第安语","parentid":"57"}]}]}]
     */

    private String message;
    private List<AlllistBean> alllist;

    public static AllClassesBean objectFromData(String str) {

        return new Gson().fromJson(str, AllClassesBean.class);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AlllistBean> getAlllist() {
        return alllist;
    }

    public void setAlllist(List<AlllistBean> alllist) {
        this.alllist = alllist;
    }

    public static class AlllistBean {
        /**
         * id : 56
         * title : 外语
         * parentid : 0
         * son : [{"id":"57","title":"小语种","parentid":"56","son":[{"id":"59","title":"梵语","parentid":"57"},{"id":"60","title":"印第安语","parentid":"57"}]}]
         */

        private String id;
        private String title;
        private String parentid;
        private List<SonBeanX> son;

        public static AlllistBean objectFromData(String str) {

            return new Gson().fromJson(str, AlllistBean.class);
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

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public List<SonBeanX> getSon() {
            return son;
        }

        public void setSon(List<SonBeanX> son) {
            this.son = son;
        }

        public static class SonBeanX {
            /**
             * id : 57
             * title : 小语种
             * parentid : 56
             * son : [{"id":"59","title":"梵语","parentid":"57"},{"id":"60","title":"印第安语","parentid":"57"}]
             */

            private String id;
            private String title;
            private String parentid;
            private List<SonBean> son;

            public static SonBeanX objectFromData(String str) {

                return new Gson().fromJson(str, SonBeanX.class);
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

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public List<SonBean> getSon() {
                return son;
            }

            public void setSon(List<SonBean> son) {
                this.son = son;
            }

            public static class SonBean {
                /**
                 * id : 59
                 * title : 梵语
                 * parentid : 57
                 */

                private String id;
                private String title;
                private String parentid;

                public static SonBean objectFromData(String str) {

                    return new Gson().fromJson(str, SonBean.class);
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

                public String getParentid() {
                    return parentid;
                }

                public void setParentid(String parentid) {
                    this.parentid = parentid;
                }
            }
        }
    }
}
