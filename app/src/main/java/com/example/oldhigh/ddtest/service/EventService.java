package com.example.oldhigh.ddtest.service;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oldhigh.ddtest.R;
import com.example.oldhigh.ddtest.bean.NewEventBean;
import com.example.oldhigh.ddtest.util.L;
import com.example.oldhigh.ddtest.util.RealmUtil;
import com.example.oldhigh.ddtest.util.RxHelper;
import com.example.oldhigh.ddtest.util.ScreenUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by oldhigh on 2017/11/16.
 */

public class EventService extends Service {


    //显示floatImage
    public static final int FLOAT_VIEW_IMAGE = 3 ;
    //显示中间布局
    public static final int FLOAT_VIEW = 4 ;

    private WindowManager mWindowManager;



    private WindowManager.LayoutParams mLayoutParams;

    private volatile boolean isAddView = false ;

    private Disposable mSubscribe;

    private View mFloatView;

    private View mFloatViewImage;




    private ClipboardManager mClipboardManager;
    private AnimatorSet mSet;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mFloatViewImage = LayoutInflater.from(this).inflate(R.layout.float_view_button, null);

        mFloatView = LayoutInflater.from(this).inflate(R.layout.float_view, null);

        mWindowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        if (mClipboardManager != null) {
            mClipboardManager.addPrimaryClipChangedListener(mListener);
        }

    }


    private NewEventBean mNewEventBean ;
    private long time= -1 ;

    //剪切板的监听回到
    private ClipboardManager.OnPrimaryClipChangedListener mListener =
            new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {

            //这里写是因为这地方会调用2次
            long curTiem = System.currentTimeMillis();
            if (time != -1 && curTiem - time < 500){
                time = curTiem ;
                return;
            }
            time  = curTiem ;

            ClipData data = mClipboardManager.getPrimaryClip();
            ClipData.Item item = data.getItemAt(0);

            L.e("复制的内容 = " + item.getText().toString());
            if (TextUtils.isEmpty(item.getText().toString().trim() )){
                return;
            }

            mNewEventBean = new NewEventBean();
            mNewEventBean.setTime(System.currentTimeMillis());
            mNewEventBean.setContent(item.getText().toString());

            RealmUtil.add(mNewEventBean);

            createImage();


        }
    };




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mClipboardManager.removePrimaryClipChangedListener(mListener);

    }

    /**
     * 先显示提示按钮
     */
    private void createImage() {

        RxHelper.checkDisposable(mSubscribe);

        //如果添加了就不在进行添加， 只是重置一下消失时间
        if (!isAddView){

            mFloatViewImage.findViewById(R.id.image_float).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createFloatView();

                    RxHelper.checkDisposable(mSubscribe);
                    mWindowManager.removeView(mFloatViewImage);
                }
            });

            windowManagerAddView(mFloatViewImage, FLOAT_VIEW_IMAGE);

            isAddView = true ;

        }
        L.e("image -- > ok。。");

        mSubscribe = Observable.interval(1 , TimeUnit.SECONDS)
                .take(6)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        animationImage(aLong);
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (isAddView && aLong == 5) {
                            mWindowManager.removeView(mFloatViewImage);
                            isAddView = !isAddView ;

                            if (mSet != null){
                                mSet.cancel();
                            }

                        }


                    }
                });

    }

    /**
     *给image做动画
     */
    private void animationImage(Long aLong) {
        if (!isAddView) {
            return;
        }
        if (aLong % 2 == 0) {
            return;
        }

        mSet = new AnimatorSet();
        mSet.playTogether(
        ObjectAnimator.ofFloat(mFloatViewImage , "scaleX" ,1 , 1.3f ,1),
        ObjectAnimator.ofFloat(mFloatViewImage , "scaleY" ,1 , 1.3f ,1)

        );

        mSet.setDuration(300).start();

    }


    /**
     * 创建浮动view
     */
    public void createFloatView(){
        windowManagerAddView(mFloatView, FLOAT_VIEW);

        TextView textView = mFloatView.findViewById(R.id.text_content);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EventService.this, "ok", Toast.LENGTH_SHORT).show();
            }
        });

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                mWindowManager.removeView(mFloatView);
                return true;
            }
        });

    }


    private void windowManagerAddView(View view, int type ){

        mLayoutParams = new WindowManager.LayoutParams();

        //TYPE_SYSTEM_ERROR
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        // 设置图片格式，效果为背景透明
        mLayoutParams.format = PixelFormat.TRANSPARENT;
        // 设置Window flag
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

  /*
         * 下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
         * wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL |
         * LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
         */


        if (type == FLOAT_VIEW){

            // 设置悬浮窗的长得宽
            mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;


            mLayoutParams.gravity = Gravity.START | Gravity.TOP;
            mLayoutParams.x = 0;
            mLayoutParams.y = 0;

        }else {

            // 设置悬浮窗的长得宽
            mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;


            mLayoutParams.gravity = Gravity.END | Gravity.TOP;
            mLayoutParams.x = 10;
            mLayoutParams.y = ScreenUtil.statusHeight(this) + ScreenUtil.toolBarHeight(this);
        }



        mWindowManager.addView(view, mLayoutParams);

    }
}
