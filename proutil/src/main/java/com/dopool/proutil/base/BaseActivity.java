package com.dopool.proutil.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dopool.proutil.R;

/**
 * Created by Karl on 2017/6/15.
 */

public class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    //LL_root
    // @BindView(R.id.ll_root)
    public LinearLayout mRoot_layout;
    //toolBar
    // @BindView(R.id.tool_bar)
    protected Toolbar mToolbar;

    private InputMethodManager mImm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(R.layout.activity_base);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
        }

        initView();
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mContext = this;


    }

    private void initView() {
        mRoot_layout = (LinearLayout) findViewById(R.id.ll_root);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
    }


    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        if (mImm != null) {
            mImm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

//------------------------------------------------//
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
//------------------------------------------------//


    private Toast mToast;

    public void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }
}
