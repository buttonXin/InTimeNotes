package com.example.oldhigh.ddtest.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import com.dopool.proutil.Helper.HttpManagerHelper;
import com.dopool.proutil.Helper.RxHelper;
import com.dopool.proutil.base.BaseActivity;
import com.dopool.proutil.base.BaseObserver;
import com.dopool.proutil.util.Logl;
import com.dopool.proutil.util.ToastUtil;
import com.example.oldhigh.ddtest.R;
import com.example.oldhigh.ddtest.api.APIInterface;
import com.example.oldhigh.ddtest.bean.GuideTabInfoBean;
import com.example.oldhigh.ddtest.bean.NewEventBean;
import com.example.oldhigh.ddtest.service.EventService;
import com.example.oldhigh.ddtest.util.L;
import com.example.oldhigh.ddtest.util.RealmUtil;

import java.io.IOException;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.oldhigh.ddtest.service.EventService.EVENT_MESSENGER;
import static com.example.oldhigh.ddtest.service.EventService.NEW_EVENT_BEAN;
import static com.example.oldhigh.ddtest.service.EventService.NEW_EVENT_WHAT;

public class MainActivity extends BaseActivity {

    private Button mButton;
    private OkHttpClient mOkHttpClient;
    private Messenger mMessenger;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_circle);

        mContext = this;

        L.e("activity " + "onCreate");

        mButton = (Button) findViewById(R.id.btn_stop);



        bindService(new Intent(this, EventService.class), mServiceConnection, BIND_AUTO_CREATE);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                testHttp();


                RealmUtil.query("name", new RealmChangeListener<RealmResults<NewEventBean>>() {
                    @Override
                    public void onChange(RealmResults<NewEventBean> newEventBeans) {
                        L.e(newEventBeans.size() + " name list ---> " + newEventBeans);

                    }
                });



            }
        });


    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);

            Message obtain = Message.obtain(null, EVENT_MESSENGER);
            obtain.replyTo = mMessengerService;

            try {
                mMessenger.send(obtain);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Handler mHandler = new MyHandler();

    private Messenger mMessengerService = new Messenger(mHandler);


    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case NEW_EVENT_WHAT:

                    NewEventBean eventBean = (NewEventBean) msg.getData().getSerializable(NEW_EVENT_BEAN);

                    ToastUtil.showTsShort(mContext, eventBean.getContent());

                    break;

                default:
                    break;
            }

        }
    }


    private void testHttp() {

        HttpManagerHelper.getInstance()
                .create(APIInterface.class)
                .getGuideTabInfoBean()
                .compose(RxHelper.<GuideTabInfoBean>ioToMain())
                .subscribe(new BaseObserver<GuideTabInfoBean>() {
                    @Override
                    public void onSuccess(GuideTabInfoBean guideTabInfoBean) {
                        Logl.e("ok --> " + guideTabInfoBean.getObjs().size());
                    }

                    @Override
                    public void onFail(Throwable t) {
                        t.printStackTrace();
                    }
                });

    }

    private String run(String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = mOkHttpClient.newCall(request).execute();

        return response.body().string();
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.e("activity " + "onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        L.e("activity " + "onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        L.e("activity " + "onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        L.e("activity " + "onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}
