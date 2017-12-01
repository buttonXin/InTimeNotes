package com.example.oldhigh.ddtest;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by oldhigh on 2017/11/24.
 */

public class MyApp extends Application {






    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);


        if (Constant.IS_DEV){

            LeakCanary.install(this);
        }
    }
}
