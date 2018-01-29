package com.qianchang.togetherlesson.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/7/23.
 */

public class Son {

    public String   id;
    public String   title;

    public String   parentid;

    public List<Sonson>  sonsonList =new ArrayList<>();
}
