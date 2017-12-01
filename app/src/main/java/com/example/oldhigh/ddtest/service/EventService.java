package com.example.oldhigh.ddtest.service;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
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

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by oldhigh on 2017/11/16.
 */

public class EventService extends Service {

    //新事件通知
    public static final int NEW_EVENT_WHAT = 1;
    //新事件对象
    public static final String NEW_EVENT_BEAN = "new_event";

    //Activity的 Messenger
    public static final int EVENT_MESSENGER = 2;


    //显示floatImage
    public static final int FLOAT_VIEW_IMAGE = 3 ;
    //显示中间布局
    public static final int FLOAT_VIEW = 4 ;

    private Handler mHandler = new MyServiceHandler();
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private boolean isAddView = false ;
    private Disposable mSubscribe;
    private View mFloatView;
    private View mFloatViewImage;

    private class MyServiceHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            if (msg.what ==  EVENT_MESSENGER){
                mMessengerActivity = msg.replyTo;
                L.e("收到Activity的MESSENGER");
            }
        }
    }

    private Messenger mMessenger  = new Messenger(mHandler);

    private Messenger mMessengerActivity;

    private ClipboardManager mClipboardManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        if (mClipboardManager != null) {
            mClipboardManager.addPrimaryClipChangedListener(mListener);
        }

    }


    private NewEventBean mNewEventBean ;
    private long time= -1 ;

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
            L.e("复制的内容 = " + TextUtils.isEmpty(item.getText().toString().trim() ) );

            mNewEventBean = new NewEventBean();
            mNewEventBean.setTime(System.currentTimeMillis());
            mNewEventBean.setContent(item.getText().toString());

            messengerSend(mNewEventBean);

            RealmUtil.add(mNewEventBean);


            RxHelper.checkDisposable(mSubscribe);

            createButton();


        }
    };



    /**
     *发送event到Activity
     */
    private void messengerSend(NewEventBean newEventBean) {
        Message message = Message.obtain(null, NEW_EVENT_WHAT);
        message.getData().putSerializable(NEW_EVENT_BEAN , mNewEventBean);
        try {

            if (mMessengerActivity != null) {
                mMessengerActivity.send(message);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


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
    private void createButton() {


        mFloatViewImage = LayoutInflater.from(this).inflate(R.layout.float_view_button, null);

        mFloatViewImage.findViewById(R.id.image_float).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFloatView();
                RxHelper.checkDisposable(mSubscribe);
                mWindowManager.removeView(mFloatViewImage);
            }
        });

        windowManagerAddView(mFloatViewImage, FLOAT_VIEW_IMAGE);

        mSubscribe = Observable.timer(6 , TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (isAddView) {
                            mWindowManager.removeView(mFloatViewImage);
                            isAddView = !isAddView ;
                        }
                    }
                });

    }


    /**
     * 创建浮动view
     */
    public void createFloatView(){


        if (isAddView){
            mWindowManager.removeView(mFloatView);
            isAddView = !isAddView ;
        }

        mFloatView = LayoutInflater.from(this).inflate(R.layout.float_view, null);


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


        isAddView = true ;
        L.e("ok。。");


    }


    private void windowManagerAddView(View view, int type ){

        mWindowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
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
            mLayoutParams.x = 0;
            mLayoutParams.y = 30;
        }



        mWindowManager.addView(view, mLayoutParams);

    }
}
