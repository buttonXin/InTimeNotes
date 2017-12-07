package com.oldhigh.intimenotes.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.oldhigh.intimenotes.R;
import com.oldhigh.intimenotes.adapter.BaseAdapterRV;
import com.oldhigh.intimenotes.adapter.EventAdapter;
import com.oldhigh.intimenotes.bean.NewEventBean;
import com.oldhigh.intimenotes.service.EventService;
import com.oldhigh.intimenotes.util.L;
import com.oldhigh.intimenotes.util.RealmUtil;
import com.oldhigh.intimenotes.util.SnackbarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.realm.RealmResults;

/**
 * Created by oldhigh on 2017/11/26.
 */

public class EventActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseAdapterRV.OnItemClickListener<NewEventBean>, EventAdapter.OnClickAdapterItemListener<Object> {

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @BindView(R.id.refresh_view)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.recycler_event)
    RecyclerView mRecycler;

    @BindView(R.id.fab)
    FloatingActionButton fab ;

    private EventAdapter mEventAdapter;
    private Unbinder mBind;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evnet);
        mBind = ButterKnife.bind(this);

        startService(new Intent(this, EventService.class));


        mEventAdapter = new EventAdapter();
        initRecycler();

        mRefreshLayout.setOnRefreshListener(this);

        mRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.tool_bar_color));


        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                initData();
            }
        });


    }

    /**
     * 从数据库获取数据
     */
    private void initData() {
        mDisposable.add(RealmUtil.queryAll()
                .subscribe(new Consumer<RealmResults<NewEventBean>>() {
                    @Override
                    public void accept(RealmResults<NewEventBean> newEventBeans) throws Exception {
                        mEventAdapter.addData(newEventBeans);
                        mRefreshLayout.setRefreshing(false);
                    }
                }) );
    }


    private void initRecycler() {
        mRecycler.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));

        mRecycler.setAdapter(mEventAdapter);

        mEventAdapter.setItemOnClickListener(this);

        mEventAdapter.setOnClickAdapterItemListener(this);

    }


    @Override
    public void onRefresh() {
        initData();

    }

    @Override
    public void onItemClick(int position,  View view,  NewEventBean eventBean) {

        showDialog(view , eventBean);
    }

    @Override
    public void onText(int position, View v, NewEventBean eventBean) {
        TextView textView = (TextView) v;

        if (textView.getLineCount() >= 3) {

            if (textView.getMaxLines() == Integer.MAX_VALUE) {
                textView.setMaxLines(3);
            } else {
                textView.setMaxLines(Integer.MAX_VALUE);
            }

        } else {
            showDialog(v, eventBean);
        }
    }

    /**
     *显示dialog
     */
    private void showDialog(final View view, final NewEventBean eventBean) {

        //取30个就行
        String content = eventBean.getContent();
        int length = content.length();
        content = length > 30 ? content.substring(0 , 30)+"..." : content ;

        new AlertDialog.Builder(this)
                .setMessage(content )
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //这里必须用new的原因是Realm 的原因，删除对象后，这个对象都无效了
                        // ， 所以不能再进行别的操作！！！切记切记！
                        showSnackBar(view ,new NewEventBean(
                                eventBean.getTime() , eventBean.getContent()));

                        mEventAdapter.removeItem(eventBean);
                        RealmUtil.deleteTime(eventBean);
                    }
                })
                .show();
    }


    /**
     * 显示snackBar来防止撤回
     * */
    private void showSnackBar(View view, final NewEventBean eventBean) {

        SnackbarUtil.snackbarTime(view, eventBean.getContent(),
                ContextCompat.getColor(mContext, R.color.white),
                ContextCompat.getColor(mContext, R.color.colorAccent),
                ContextCompat.getColor(mContext, R.color.tool_bar_color),
                3000)
                .setAction("撤回", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEventAdapter.addItem(eventBean);
                        L.e("event = " + eventBean.toString());
                        RealmUtil.add(eventBean);
                    }
                })
                .show();
    }

    @OnClick({R.id.fab })
    public void onClickView(View view){
        switch (view.getId()){

            case R.id.fab:
                _startActivity(AddEventActivity.class);
                break;


            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
        mBind.unbind();
    }
}
