package com.example.oldhigh.ddtest.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.oldhigh.ddtest.R;
import com.example.oldhigh.ddtest.ui.RippleView;

/**
 * Created by oldhigh on 2017/12/3.
 */

public class ViewActivity extends BaseActivity {

    private ImageView mImageView;
    private RippleView mRippleView;
    private Paint mRipplePaint;
    private int mRippleWidth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        mRippleView = findViewById(R.id.ripple);
        mRippleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRippleView.startRipple();
            }
        });


    }
}
