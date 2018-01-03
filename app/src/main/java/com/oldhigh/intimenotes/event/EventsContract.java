package com.oldhigh.intimenotes.event;

import com.oldhigh.intimenotes.base.BasePresenter;
import com.oldhigh.intimenotes.base.BaseView;
import com.oldhigh.intimenotes.data.NewEventBean;

import java.util.List;

/**
 *
 * @author oldhigh
 * @date 2018/1/2
 */

public interface EventsContract {

    interface EventView extends BaseView<EventPresenter>{

        void setLoadingIndicator(boolean active);

        void showEvents(List<NewEventBean> eventBeans);

        void addEvent(NewEventBean eventBean);

        void showEventsError();


    }

    interface EventPresenter extends BasePresenter{

        void loadEvents(boolean forceUpdate);

        void addEvent(NewEventBean newEventBean);

        void deleteEvent(NewEventBean eventBean);

        void moveEvent(NewEventBean current, NewEventBean eventBean);


    }
}
