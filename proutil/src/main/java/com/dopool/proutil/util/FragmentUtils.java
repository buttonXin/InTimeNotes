package com.dopool.proutil.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Karl on 2017/6/15.
 *
 */

public class FragmentUtils {

    /**
     * 替换
     * @param manager manager
     * @param fragment fragment
     * @param resId resid
     */
    public static void replaceFragment(FragmentManager manager, Fragment fragment, int resId){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(resId,fragment);
        transaction.commit();
    }
}
