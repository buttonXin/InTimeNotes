package com.example.oldhigh.ddtest.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.oldhigh.ddtest.util.L;


/**
 * Created by oldhigh on 2017/11/9.
 */

public class TimeCircleView extends View {

    private int mArg1;
    private  Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mArg1 = (int) msg.obj;
            invalidate();
            L.d("marg1 = " +mArg1);

        }
    };
    private Paint mPaint;
    private int mPosition;

    public TimeCircleView(Context context) {
        this(context ,null);
    }

    public TimeCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public TimeCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(5);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mPosition = Math.min(widthMeasureSpec, heightMeasureSpec)/2;

    }

    private int angle ;
    private boolean isStop = true;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(200 , 200);

        canvas.drawCircle(100 , 100 , 100 , mPaint);

        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLACK);

        canvas.drawArc(0 , 0 , 200 , 200 , -90 , angle , false , mPaint);
    }


    public void startCircle(){
        angle = 0 ;
        post(new Runnable() {
            @Override
            public void run() {
                while (angle < 60 ) {

                    new View(getContext());
                    L.d("angle = " + Thread.currentThread().getName() + "  " + angle);
                    if (!isStop) {
                        break;
                    }
                    angle += 6;
                    postInvalidate();
                    Message message = new Message();
                    message.obj = angle;
                    mHandler.sendMessage(message);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void stop(){
        isStop  = false;
    }

}
