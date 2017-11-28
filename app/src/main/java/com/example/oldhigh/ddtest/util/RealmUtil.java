package com.example.oldhigh.ddtest.util;

import android.support.annotation.NonNull;

import com.example.oldhigh.ddtest.bean.NewEventBean;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * 数据库的工具栏 ， 需要进行单列
 * Created by oldhigh on 2017/11/26.
 */

public class RealmUtil {


    /**
     * 增加数据到数据库
     */
    public static void add(final NewEventBean eventBean) {

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.copyToRealm(eventBean);

        realm.commitTransaction();

        realm.close();


    }


    /**
     * 查找所有数据
     */
    public static void queryAll(
                             RealmChangeListener<RealmResults<NewEventBean>> listener) {

        Realm realm = Realm.getDefaultInstance();

        realm.where(NewEventBean.class)
                //根据时间来进行降序排序， 最新的放到最上面
                .findAllSortedAsync("time" , Sort.DESCENDING)
                .addChangeListener(listener);

    }
    /**
     * 查找数据
     */
    public static void query(String content ,
                             RealmChangeListener<RealmResults<NewEventBean>> listener) {

        Realm realm = Realm.getDefaultInstance();

        realm.where(NewEventBean.class)
                .equalTo("content", content)
                .findAllAsync()
                .addChangeListener(listener);

    }

    /**
     * 修改数据
     */
    public static void modify(Realm.Transaction transaction) {

        Realm.getDefaultInstance().executeTransactionAsync(transaction);

    }

    /**
     * 删除数据
     */
    public static void delete(NewEventBean eventBean) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(NewEventBean.class)
                .equalTo("content", eventBean.getContent())
                .findAll()
                .deleteAllFromRealm();

        realm.commitTransaction();

        realm.close();

    }


}
