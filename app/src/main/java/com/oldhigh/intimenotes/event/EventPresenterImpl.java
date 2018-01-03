package com.oldhigh.intimenotes.event;

import android.content.Context;

import com.oldhigh.intimenotes.data.NewEventBean;
import com.oldhigh.intimenotes.data.EventDataSource;
import com.oldhigh.intimenotes.data.EventRemoteDataSource;

import java.util.List;

/**
 * Created by oldhigh on 2018/1/2.
 */

public class EventPresenterImpl implements EventsContract.EventPresenter {


    private final EventRemoteDataSource mEventRemoteDataSource;

    private final EventsContract.EventView mView;

    public EventPresenterImpl(Context context , EventsContract.EventView view) {
        mEventRemoteDataSource = new EventRemoteDataSource(context);
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void loadEvents(boolean forceUpdate) {
        loadEvents(forceUpdate , true);
    }

    private void loadEvents(boolean forceUpdate, final boolean refresh) {
        if (forceUpdate){
            // TODO: 2018/1/2   mEventRemoteDataSource 进行强制刷新
        }
        if (refresh){
            mView.setLoadingIndicator(true);
        }
        //从数据库取数据
        mEventRemoteDataSource.getEvents(new EventDataSource.LoadEventsCallback() {
            @Override
            public void onEventsLoaded(List<NewEventBean> tasks) {
                mView.showEvents(tasks);

                if (refresh){
                    mView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void addEvent(NewEventBean eventBean) {
        if (eventBean == null){
            mView.addEvent(null);

        }else {
            mEventRemoteDataSource.saveEvent(eventBean);
        }
    }

    @Override
    public void deleteEvent(NewEventBean eventBean) {
        mEventRemoteDataSource.deleteEvent(eventBean);
    }

    @Override
    public void moveEvent(NewEventBean current, NewEventBean toEvent) {
//        //移动代表删除当前的 ，添加
//        mEventRemoteDataSource.deleteEvent(toEvent);
//
//        mEventRemoteDataSource.saveEvent(toEvent.copyEvent(toEvent));
    }

    @Override
    public void start() {
        loadEvents(true);
    }
}
