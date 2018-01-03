package com.oldhigh.intimenotes.util;

import android.util.Log;

import java.util.Arrays;

/**
 * Created by oldhigh on 2017/11/4.
 */

public class L {


    public static void d(String args) {
        Log.d(Thread.currentThread().getName(), "d: "+  args);
    }

    public static void e(Object... args) {
        Log.e(Thread.currentThread().getName(),  Arrays.toString(args));
    }
}
