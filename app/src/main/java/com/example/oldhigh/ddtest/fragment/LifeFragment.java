package com.example.oldhigh.ddtest.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oldhigh.ddtest.util.L;

/**
 * Created by oldhigh on 2017/11/4.
 */

public class LifeFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        L.e("onAttach");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.e("onCreate");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        L.e("onCreateView");


        TextView textView = new TextView(getContext());
        textView.setText("444444444444");
        textView.setTextSize(30f);
        return textView;
    }

    @Override
    public void onStart() {
        super.onStart();
        L.e("onStart");



    }

    @Override
    public void onResume() {
        super.onResume();
        L.e("onResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        L.e("onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        L.e("onStop");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.e("onDestroyView");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.e("onDestroy");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        L.e("onDetach");

    }
}
