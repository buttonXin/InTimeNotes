package com.oldhigh.intimenotes.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by oldhigh on 2018/1/2.
 */

public interface EventDataSource {

    interface LoadEventsCallback {

        void onEventsLoaded(List<NewEventBean> tasks);

        void onDataNotAvailable();
    }

    void getEvents(@NonNull LoadEventsCallback callback);

    void saveEvent(@NonNull NewEventBean eventBean);

    void deleteEvent(@NonNull NewEventBean eventBean);

    void deleteAllEvents();

}
