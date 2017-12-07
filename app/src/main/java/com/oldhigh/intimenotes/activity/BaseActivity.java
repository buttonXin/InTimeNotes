package com.oldhigh.intimenotes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.oldhigh.intimenotes.R;

/**
 * Created by oldhigh on 2017/11/29.
 */

public class BaseActivity  extends AppCompatActivity{

    private LinearLayout mRoot_layout;
    private Toolbar mToolbar;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        super.setContentView(R.layout.activity_base);

        mContext = this ;
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(ContextCompat.getColor(this , R.color.status_color));

        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }

        initView();
    }

    private void initView() {
        mRoot_layout = (LinearLayout) findViewById(R.id.ll_root);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
    }

    /**
     * 下面3个方法是为了toolbar的使用的
     */
    private void initToolBar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        if (mRoot_layout == null) {
            return;
        }
        mRoot_layout.addView(view, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        initToolBar();
    }



    protected void _startActivity(Class clazz){
        startActivity(new Intent(this ,clazz));
    }

}
