package com.qianchang.togetherlesson.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/7/23.
 */

public class ClassesBean {
  public String  id;
    public String  title;
    public String  applynum;
    public String  cost;
    public String  picurl;
    public String  flag;
    public String  times;
    public List<FlaglistBean> ziCommentBeanList = new ArrayList<>();
}
