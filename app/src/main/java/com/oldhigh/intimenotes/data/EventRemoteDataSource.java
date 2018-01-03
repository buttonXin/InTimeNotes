package com.oldhigh.intimenotes.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.oldhigh.intimenotes.util.RealmUtil;

import io.reactivex.functions.Consumer;
import io.realm.RealmResults;

/**
 * Created by oldhigh on 2018/1/2.
 */

public class EventRemoteDataSource implements EventDataSource {

    private Context mContext;

    public EventRemoteDataSource(Context context) {
        mContext = context;
    }


    @Override
    public void getEvents(@NonNull final LoadEventsCallback callback) {
        RealmUtil.queryAll()
                .subscribe(new Consumer<RealmResults<NewEventBean>>() {
                    @Override
                    public void accept(RealmResults<NewEventBean> newEventBeans) throws Exception {

                        callback.onEventsLoaded(newEventBeans);
                    }
                });
    }

    @Override
    public void saveEvent(@NonNull NewEventBean eventBean) {
        RealmUtil.add(eventBean);
    }

    @Override
    public void deleteEvent(@NonNull NewEventBean eventBean) {
        RealmUtil.deleteTime(eventBean);
    }

    @Override
    public void deleteAllEvents() {
        RealmUtil.deleteAll();
    }
}
