package com.example.oldhigh.ddtest.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

/**
 * Created by oldhigh on 2017/12/3.
 */

public class RippleView extends View {

    private Paint mPaint;
    private Paint mRipplePaint;

    public RippleView(Context context) {
        this(context , null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);

        mRipplePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRipplePaint.setStyle(Paint.Style.STROKE);
        mRipplePaint.setStrokeWidth(0);
        mRipplePaint.setColor(Color.TRANSPARENT);

        setBackgroundColor(Color.RED);
    }

    private int centerX , centerY = 50 ;
    private int radius = 50 ;
    private int mRippleWidth = 50 ;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(centerX *2 , centerY * 2);
        canvas.drawCircle(centerX , centerY , radius , mPaint);

        canvas.drawCircle(centerX , centerY , radius + mRippleWidth/2 , mRipplePaint);

    }

    private int r = 255, g = 255 , b = 255 ;

    public void startRipple(){
        mRipplePaint.setARGB(127, r-- , g--, b--);

        ValueAnimator va = ValueAnimator.ofInt(0, mRippleWidth);
        va.setDuration(500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (int) animation.getAnimatedValue();
                mRippleWidth = (int) value;


                setAlpha((float) (0.5 - (value/50/2)));

                mRipplePaint.setStrokeWidth(value);
                invalidate();
            }
        });

        va.start();

    }
}
