package com.example.oldhigh.ddtest.util;

import com.example.oldhigh.ddtest.bean.NewEventBean;

import java.util.Comparator;

/**
 * Created by oldhigh on 2017/11/27.
 */

public class SortUtil implements Comparator<NewEventBean> {
    @Override
    public int compare(NewEventBean o1, NewEventBean o2) {
        //这里注意一下， 这是从小到大的顺序，如果改成 < 就是从大到小了！
        if (o1.getTime() > o2.getTime()){
            return 1 ;
        }else if (o1.getTime() == o2.getTime()){
            return 0 ;
        }else {
            return -1;
        }

    }
}
