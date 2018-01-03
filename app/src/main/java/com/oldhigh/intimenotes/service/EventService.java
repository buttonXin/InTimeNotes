package com.oldhigh.intimenotes.service;

import android.animation.AnimatorSet;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.oldhigh.intimenotes.data.NewEventBean;
import com.oldhigh.intimenotes.util.FloatViewUtil;
import com.oldhigh.intimenotes.util.L;

/**
 * Created by oldhigh on 2017/11/16.
 */

public class EventService extends Service {

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

        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        if (mClipboardManager != null) {
            mClipboardManager.addPrimaryClipChangedListener(mListener);
        }

        mFloatViewUtil = new FloatViewUtil(getApplicationContext());

    }


    private long time= -1 ;

    private FloatViewUtil mFloatViewUtil;
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

            NewEventBean newEventBean = new NewEventBean();
            newEventBean.setTime(System.currentTimeMillis());
            newEventBean.setContent(item.getText().toString().trim());

            if (mFloatViewUtil == null){
                mFloatViewUtil = new FloatViewUtil(getApplicationContext());
            }else {
                mFloatViewUtil.createImage(newEventBean);
            }

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

        if (mFloatViewUtil != null){
            mFloatViewUtil.clear();
        }
    }


}
