package com.oldhigh.intimenotes.util;

import com.oldhigh.intimenotes.bean.NewEventBean;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.Realm;
import io.realm.RealmChangeListener;
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
        Realm realm = null;

        try {

            realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            //先删除相同内容的， 在添加进去，也就算更新内容了
            realm.where(NewEventBean.class)
                    .equalTo("content", eventBean.getContent().trim())
                    .findAll()
                    .deleteAllFromRealm();

            realm.copyToRealm(eventBean);


            realm.commitTransaction();

            L.e("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            if (realm != null && realm.isInTransaction()) {
                realm.cancelTransaction();
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }



    }


    /**
     * 查找所有数据
     */
    public static Flowable<RealmResults<NewEventBean>> queryAll() {




        Realm realm = Realm.getDefaultInstance();


        return realm.where(NewEventBean.class)
                .sort("time" , Sort.DESCENDING)
                .findAllAsync().asFlowable().observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 查找数据
     */
    public static void query(String content,
                             RealmChangeListener<RealmResults<NewEventBean>> listener) {

        Realm realm = Realm.getDefaultInstance();

        realm.where(NewEventBean.class)
                .equalTo("content", content.trim())
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
     * 删除相同内容和时间的数据
     */
    public static void deleteTime(final NewEventBean eventBean) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(NewEventBean.class)
                .equalTo("time", eventBean.getTime())
                .findAll()
                .deleteAllFromRealm();

        realm.commitTransaction();

        realm.close();

        L.e("删除数据");

    }

  /*  *//**
     * 删除相同内容的数据
     *//*
    public static void deleteContent(NewEventBean eventBean) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(NewEventBean.class)
                .equalTo("content", eventBean.getContent().trim())
                .findAll()
                .deleteAllFromRealm();

        realm.commitTransaction();

        realm.close();

    }*/

    /**
     * 删除全部数据
     */
    public static void deleteAll(NewEventBean eventBean) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(NewEventBean.class)
                .findAll()
                .deleteAllFromRealm();

        realm.commitTransaction();

        realm.close();

    }


}
