package com.oldhigh.intimenotes.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.oldhigh.intimenotes.R;
import com.oldhigh.intimenotes.service.EventService;
import com.oldhigh.intimenotes.util.ActivityUtils;
import com.oldhigh.intimenotes.util.RealmUtil;

/**
 * Created by oldhigh on 2018/1/2.
 */

public class EventsActivity extends AppCompatActivity {


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


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RealmUtil.closeRealm();
    }
}
