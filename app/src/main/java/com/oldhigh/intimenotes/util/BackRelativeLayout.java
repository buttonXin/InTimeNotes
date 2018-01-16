package com.oldhigh.intimenotes.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

/**
 *  写这个主要是为了将 KeyEvent 的事件传递出来
 * @author oldhigh
 * @date 2018/1/16
 */

public class BackRelativeLayout extends RelativeLayout {


    private OnKeyEventListener mListener;

    public BackRelativeLayout(Context context) {
        super(context);
    }

    public BackRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (mListener != null){
            return  mListener.onKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }



    public void setOnKeyEventListener(OnKeyEventListener listener){
        mListener = listener;
    }
    public interface OnKeyEventListener{

        boolean onKeyEvent(KeyEvent event);
    }
}
