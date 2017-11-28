package com.example.oldhigh.ddtest.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;

import io.realm.RealmObject;

/**
 * 当粘贴的内容来临的时候， 就创建当前对象
 * Created by oldhigh on 2017/11/26.
 */

public class NewEventBean extends RealmObject implements Serializable {

    //创建的时间
    private long time ;
    //粘贴板的内容
    private String content ;


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
