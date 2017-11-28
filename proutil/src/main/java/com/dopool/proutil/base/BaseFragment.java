package com.dopool.proutil.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Karl on 2017/7/14.
 *
 */

public class BaseFragment extends Fragment {


   // protected AppCompatActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       // mActivity = (AppCompatActivity) context;

    }


}
