package com.example.oldhigh.ddtest.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by oldhigh on 2017/11/30.
 */

public class ScreenUtil {


    /**
     *获取状态栏的高度
     */
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

    /**
     * 获取toolbar 的高度
     */
    public static int toolBarHeight(Context context){

        int  actionBarHeight =  convertDipToPx(context , 40);

        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(
                    android.support.v7.appcompat.R.attr.actionBarSize, tv, true)) {

            actionBarHeight = TypedValue.complexToDimensionPixelSize(
                    tv.data,context.getResources().getDisplayMetrics() );
        }


        return actionBarHeight ;
    }



    //转换dp为px
    public static int convertDipToPx(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    //转换px为dp
    public static int convertPxToDip(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }
    //转换sp为px
    public static int spTopx(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    //转换px为sp
    public static int pxTosp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}
