package com.example.oldhigh.ddtest;

import com.dopool.proutil.BaseApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by oldhigh on 2017/11/24.
 */

public class MyApp extends BaseApplication {



    public MyApp(){
        super(Constant.IS_DEV , "test->" , Constant.BASE_URL);

    }


    @Override
    public void onCreate() {
        super.onCreate();


        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

    }
}
