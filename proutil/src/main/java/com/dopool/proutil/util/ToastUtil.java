package com.dopool.proutil.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class ToastUtil {

    private static Toast mToast ;

    /**
     * 吐司
     */
    public static void showTsShort(Context context , String content){
        if (mToast == null){
            mToast = Toast.makeText(context , content , Toast.LENGTH_SHORT);
        }else {
            mToast.setText(content);
        }
        mToast.show();
    }

    public static void showTsLong(Context context , String content){
        if (mToast == null){
            mToast = Toast.makeText(context , content , Toast.LENGTH_LONG);
        }else {
            mToast.setText(content);
        }
        mToast.show();
    }



}
