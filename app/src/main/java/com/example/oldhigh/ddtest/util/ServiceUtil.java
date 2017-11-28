package com.example.oldhigh.ddtest.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import com.example.oldhigh.ddtest.service.EventService;

import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by oldhigh on 2017/11/26.
 */

public class ServiceUtil {


    public static void  checkService(Context context, ServiceConnection serviceConnection){

        final ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services =
                activityManager.getRunningServices(100);


        L.e("service size = " + services.size());
        for (int i = 0; i < services.size(); i++) {
            //如果有就 退出
            if (services.get(i).service.getClassName().contains("com.example.oldhigh.ddtest")){
                return;
            }
        }

        //没有就绑定
        L.e("没有绑定");
        context.bindService(new Intent(context , EventService.class)
                , serviceConnection , BIND_AUTO_CREATE);

        context.startService(new Intent(context , EventService.class));

    }

}
