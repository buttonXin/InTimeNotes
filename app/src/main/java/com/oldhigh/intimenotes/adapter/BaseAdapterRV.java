package com.oldhigh.intimenotes.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public abstract class BaseAdapterRV<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<T> mList = new ArrayList<>();

    private OnItemClickListener mOnItemClickListener;//点击事件
    //删除 和添加数据 的位置
    private int mPosition;

    /**
     * 添加数据
     */
    public void addData(List<T> datas) {
        mList.clear();
        mList.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 获取当前的集合
     */
    public List<T> getList() {

        return mList;
    }

    //清除数据
    public void cleanData() {
        mList.clear();
    }

    //删除单条数据
    public void deleteData(int pos) {
        mList.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createVHolder(parent, viewType);
    }


    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final T t = mList.get(position);
        onBindVH(holder, position, t);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, holder.itemView, t);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 添加数据
     */
    public void addItem(T t) {
        mList.add(mPosition, t);
        notifyItemInserted(mPosition);
    }

    /**
     * 交换数据
     */
    public void onItemDragged(int from, int to) {
        Collections.swap(mList, from, to);
        notifyItemMoved(from, to);
    }

    /**
     * 删除数据,如果用notifyDataSetChanged();没有动画效果
     */
    public T removeItem(T t) {
        mPosition = mList.indexOf(t);
        mList.remove(mPosition);
        notifyItemRemoved(mPosition);
        return t;
    }
    /**
     * 删除数据,如果用notifyDataSetChanged();没有动画效果
     */
    public T removeItem(int  position) {
        T t = mList.remove(position);
        notifyItemRemoved(position);
        return t;
    }

    /**
     *获取当前位置的T类
     */
    public T getCurrent(int position){

        return mList.get(position);
    }


    public abstract RecyclerView.ViewHolder createVHolder(ViewGroup parent, int viewType);

    protected abstract void onBindVH(RecyclerView.ViewHolder viewHolder, int pos, T t);

    public static abstract class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }

    public <T> void setItemOnClickListener(OnItemClickListener<T> listener) {
        mOnItemClickListener = listener;
    }


    public interface OnItemClickListener<T> {
        void onItemClick(int position, View view, T t);
    }

}
