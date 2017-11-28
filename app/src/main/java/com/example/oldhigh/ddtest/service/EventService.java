package com.example.oldhigh.ddtest.service;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.dopool.proutil.util.ToastUtil;
import com.example.oldhigh.ddtest.bean.NewEventBean;
import com.example.oldhigh.ddtest.util.L;
import com.example.oldhigh.ddtest.util.RealmUtil;

import io.realm.Realm;

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

    public static final int EVENT_3 = 3;
    public static final int EVENT_4 = 4;


    private Handler mHandler = new MyServiceHandler();

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
            ToastUtil.showTsShort(getBaseContext() , "发送粘贴板内容失败");
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
}
