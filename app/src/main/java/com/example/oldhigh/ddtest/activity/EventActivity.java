package com.example.oldhigh.ddtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.oldhigh.ddtest.R;
import com.example.oldhigh.ddtest.adapter.BaseAdapterRV;
import com.example.oldhigh.ddtest.adapter.EventAdapter;
import com.example.oldhigh.ddtest.bean.NewEventBean;
import com.example.oldhigh.ddtest.service.EventService;
import com.example.oldhigh.ddtest.util.L;
import com.example.oldhigh.ddtest.util.RealmUtil;
import com.example.oldhigh.ddtest.util.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by oldhigh on 2017/11/26.
 */

public class EventActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh_view)
    SwipeRefreshLayout mRefreshLayout ;

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
        initRecycler();
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this , R.color.tool_bar_color));


        initData();


        L.e("---> " + ScreenUtil.statusHeight(this));
    }

    private void initData() {

        RealmUtil.queryAll(new RealmChangeListener<RealmResults<NewEventBean>>() {
            @Override
            public void onChange(RealmResults<NewEventBean> newEventBeans) {

                L.e("size = " + newEventBeans.size());

                mEventAdapter.addData(newEventBeans);
                mRefreshLayout.setRefreshing(false);
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
        Snackbar.make(view , eventBean.getContent() , 3000)
                .setAction("确定删除", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEventAdapter.removeItem(eventBean);
                        RealmUtil.delete(eventBean);

                    }
                })
                .show();
    }


    @Override
    public void onRefresh() {
        initData();

    }
}
