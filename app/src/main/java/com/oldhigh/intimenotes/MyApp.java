package com.oldhigh.intimenotes;

import android.app.Application;

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


        if (BuildConfig.IS_DEBUG){

            //LeakCanary.install(this);
        }


    }
}
