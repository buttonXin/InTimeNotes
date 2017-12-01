package com.example.oldhigh.ddtest.util;

import android.content.Context;

/**
 * Created by oldhigh on 2017/11/30.
 */

public class ScreenUtil {


    public static int statusHeight(Context context){
        int statusBarHeight1 = -1;
//获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight1 ;
    }
}
