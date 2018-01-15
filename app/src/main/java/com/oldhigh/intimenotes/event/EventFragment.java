package com.oldhigh.intimenotes.event;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.oldhigh.intimenotes.R;
import com.oldhigh.intimenotes.adapter.BaseAdapterRV;
import com.oldhigh.intimenotes.adapter.EventAdapter;
import com.oldhigh.intimenotes.data.NewEventBean;
import com.oldhigh.intimenotes.util.L;
import com.oldhigh.intimenotes.util.SnackbarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by oldhigh on 2018/1/2.
 */

public class EventFragment extends Fragment implements EventsContract.EventView{

    @BindView(R.id.refresh_view)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.recycler_event)
    RecyclerView mRecycler;


    private EventAdapter mEventAdapter;
    private Unbinder mBind;


    //mvp --> p
    private EventsContract.EventPresenter mPresenter;



    public static EventFragment newInstance() {
        return new EventFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEventAdapter = new EventAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_event, container , false);

        mBind = ButterKnife.bind(this , view);

        initRecycler();

        mRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getContext(), R.color.tool_bar_color));

        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.loadEvents(true);
            }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadEvents(true);
            }
        });

        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_task_done);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addEvent(null);
            }
        });

        return view ;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadEvents(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mBind.unbind();
    }

    private void initRecycler() {
        mRecycler.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false));

        mRecycler.setAdapter(mEventAdapter);


        mEventAdapter.setItemOnClickListener(new BaseAdapterRV.OnItemClickListener<NewEventBean>() {
            @Override
            public void onItemClick(int position, View view, NewEventBean eventBean) {
                showDialog(view , eventBean);
            }
        });

        mEventAdapter.setOnClickAdapterItemListener(new EventAdapter.OnClickAdapterItemListener<NewEventBean>() {
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
        });

        initRVAnimation(mRecycler);
    }

    /**
     *侧滑  和拖拽 事件
     */
    private void initRVAnimation(RecyclerView recycler) {
        //为RecycleView绑定触摸事件
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //侧滑删除
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                //首先回调的方法 返回int表示是否监听该方向
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                //拖拽
                return makeMovementFlags(dragFlags, swipeFlags);
            }
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //拖拽事件 ， 也就是移动item
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                mEventAdapter.onItemDragged(toPosition, fromPosition);

                NewEventBean fromBean = mEventAdapter.getCurrent(fromPosition);
                NewEventBean toBean = mEventAdapter.getCurrent(toPosition);


                mPresenter.moveEvent(fromBean, toBean);

                return true;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //侧滑事件 要先获取实体类，顺序不能动！！！ 在进行删除，要不会数组越界
                NewEventBean eventBean = mEventAdapter.getCurrent(viewHolder.getAdapterPosition());
                showSnackBar(viewHolder.itemView , eventBean.copyEvent() );

                mPresenter.deleteEvent(eventBean);
                mEventAdapter.removeItem(viewHolder.getAdapterPosition() );
            }
            @Override
            public boolean isLongPressDragEnabled() {
                //是否可拖拽
                return true;
            }

        });
        helper.attachToRecyclerView(recycler);
    }




    @Override
    public void setPresenter(EventsContract.EventPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {

        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(active);
            }
        });

    }

    @Override
    public void showEvents(List<NewEventBean> eventBeans) {
        mEventAdapter.addData(eventBeans);
    }

    @Override
    public void addEvent(NewEventBean eventBean) {

        startActivity(new Intent(getContext() , AddEventActivity.class));
    }



    @Override
    public void showEventsError() {
        Toast.makeText(getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
    }





    /**
     *显示dialog
     */
    private void showDialog(final View view, final NewEventBean eventBean) {

        //取30个就行
        String content = eventBean.getContent();
        int length = content.length();
        content = length > 30 ? content.substring(0 , 30)+"..." : content ;

        new AlertDialog.Builder(getActivity())
                .setMessage(content )
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //这里必须用new的原因是Realm 的原因，删除对象后，这个对象都无效了
                        // ， 所以不能再进行别的操作！！！切记切记！
                        showSnackBar(view , eventBean.copyEvent());

                        mEventAdapter.removeItem(eventBean);

                        mPresenter.deleteEvent(eventBean);
                    }
                })
                .show();
    }


    /**
     * 显示snackBar来防止撤回
     * */
    private void showSnackBar(View view, final NewEventBean eventBean) {

        SnackbarUtil.snackbarTime(view, eventBean.getContent(),
                ContextCompat.getColor(getContext(), R.color.white),
                ContextCompat.getColor(getContext(), R.color.colorAccent),
                ContextCompat.getColor(getContext(), R.color.tool_bar_color),
                3000)
                .setAction("撤回", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEventAdapter.addItem(eventBean);
                        L.e("event = " + eventBean.toString());
                        mPresenter.addEvent(eventBean);
                    }
                })
                .show();
    }
}
