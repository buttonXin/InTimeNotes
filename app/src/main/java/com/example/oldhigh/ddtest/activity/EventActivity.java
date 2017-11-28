package com.example.oldhigh.ddtest.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dopool.proutil.base.BaseActivity;
import com.dopool.proutil.base.BaseAdapterRV;
import com.dopool.proutil.util.ToastUtil;
import com.example.oldhigh.ddtest.R;
import com.example.oldhigh.ddtest.adapter.EventAdapter;
import com.example.oldhigh.ddtest.bean.NewEventBean;
import com.example.oldhigh.ddtest.service.EventService;
import com.example.oldhigh.ddtest.util.L;
import com.example.oldhigh.ddtest.util.RealmUtil;
import com.example.oldhigh.ddtest.util.SortUtil;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by oldhigh on 2017/11/26.
 */

public class EventActivity extends BaseActivity{

    @BindView(R.id.recycler_event)
    RecyclerView mRecycler;

    private EventAdapter mEventAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evnet);


        startService(new Intent(this , EventService.class));

        ButterKnife.bind(this);

        mEventAdapter = new EventAdapter();

        initData();

        initRecycler();
    }

    private void initData() {

        RealmUtil.queryAll(new RealmChangeListener<RealmResults<NewEventBean>>() {
            @Override
            public void onChange(RealmResults<NewEventBean> newEventBeans) {

                L.e("size = " + newEventBeans.size());



                mEventAdapter.addData(newEventBeans);
            }
        });
    }

    private void initRecycler() {
        mRecycler.setLayoutManager(new LinearLayoutManager(
                this , LinearLayoutManager.VERTICAL , false));

        mRecycler.setAdapter(mEventAdapter);

        mEventAdapter.setItemOnClickListener(new BaseAdapterRV.OnItemClickListener<NewEventBean>() {
            @Override
            public void onItemClick(int position, View view, NewEventBean eventBean) {
                showSnackBar(view , eventBean);
            }
        });
    }

    private void showSnackBar(View view, final NewEventBean eventBean) {
        Snackbar.make(view , eventBean.getContent() , 5000)
                .setAction("确定删除", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEventAdapter.removeItem(eventBean);
                        RealmUtil.delete(eventBean);

                    }
                })
                .show();
    }

}
