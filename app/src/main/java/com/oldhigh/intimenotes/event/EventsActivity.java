package com.oldhigh.intimenotes.event;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.oldhigh.intimenotes.R;
import com.oldhigh.intimenotes.service.EventService;
import com.oldhigh.intimenotes.util.ActivityUtils;
import com.oldhigh.intimenotes.util.RealmUtil;

/**
 * Created by oldhigh on 2018/1/2.
 */

public class EventsActivity extends AppCompatActivity {


    private static final int SYSTEM_ALERT_WINDOW_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evnets);

        //启动后台服务 剪切板的监听进程
        startService(new Intent(this, EventService.class));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        EventFragment eventFragment =
                (EventFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (eventFragment == null) {
            // Create the fragment
            eventFragment = EventFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), eventFragment, R.id.contentFrame);
        }

        EventPresenterImpl mEventPresenter = new EventPresenterImpl(this, eventFragment);


        //先不加悬浮权限
        permissions();
    }

    private void permissions() {

        if(Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {

                new AlertDialog.Builder(this)
                        .setMessage("请将权限中的悬浮窗打开，使应用允许出现在其他app的上面" )
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "你没有允许悬浮窗权限，那我的这app还有啥亮点。。。", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                            Uri.parse("package:" + getPackageName()));
                                    startActivity(intent);
                                }catch (Exception e){

                                    Intent localIntent = new Intent();
                                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                    localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                                    startActivityForResult(localIntent , SYSTEM_ALERT_WINDOW_REQUEST_CODE);
                                }
                            }
                        })
                        .setCancelable(false)
                        .show();

            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (Settings.canDrawOverlays(this)){

                Toast.makeText(this, "可以退出应用，然后去别的应用中进行复制文字", Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(this, "没有允许悬浮窗权限，那我的这app还有啥亮点。。。", Toast.LENGTH_LONG).show();

            }
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RealmUtil.closeRealm();
    }
}
