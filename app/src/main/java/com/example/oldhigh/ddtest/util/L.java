package com.example.oldhigh.ddtest.util;

import android.util.Log;

/**
 * Created by oldhigh on 2017/11/4.
 */

public class L {


    public static void d(String args) {
        Log.d(Thread.currentThread().getName(), "d: "+  args);
    }

    public static void e(String args) {
        Log.e(Thread.currentThread().getName(), "d: "+  args);
    }
}
