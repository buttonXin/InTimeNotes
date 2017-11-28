package com.dopool.proutil.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dopool.proutil.Helper.ImageLdHelper;
import com.dopool.proutil.R;

/**
 * Created by oldhigh on 2017/11/21.
 */

public class DialogUtil {

    private static ProgressDialog progressDialog;


    private  View mFl_progress;
    public final ImageView mImage_no_network;

    /**
     * 显示dialog
     *
     * @param context
     */
    public static void showDialog(Context context) {
        progressDialog = new ProgressDialog(context, android.R.style.Theme_Material_Light_Dialog);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("加载中...");
        progressDialog.show();
        Logl.e("show");
    }

    /**
     * 隐藏dialog
     */
    public static void dismissDialog() {
        Logl.e("dismiss");

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null ;
        }
    }





    /**
     * 这里需要在Activity 的setContentView  、fragment的onCreateView 下进行添加
     * */
    public DialogUtil(View view) {


        mFl_progress = LayoutInflater.from(view.getContext())
                .inflate(R.layout.progrss_bar_network, null, false);

        if (view instanceof LinearLayout ){
            ((ViewGroup) view).addView(mFl_progress , 0 ,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,ViewGroup.LayoutParams.MATCH_PARENT ));

        }

        if (view instanceof RelativeLayout || view instanceof FrameLayout){
            ((ViewGroup) view).addView(mFl_progress ,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,ViewGroup.LayoutParams.MATCH_PARENT ));

        }
        //不推荐在ConstraintLayout的根布局使用这个，因为没有测过，如果使用过程中没有问题就尽管用了
        if (view instanceof ConstraintLayout){

            ((ConstraintLayout) view).addView(mFl_progress );
            ConstraintSet set = new ConstraintSet();
            set.clone((ConstraintLayout) view);
            set.connect(mFl_progress.getId(), ConstraintSet.END,
                    ConstraintSet.PARENT_ID,ConstraintSet.END);
            set.connect(mFl_progress.getId(),ConstraintSet.START,
                    ConstraintSet.PARENT_ID,ConstraintSet.START);
            set.constrainWidth(mFl_progress.getId() , ConstraintLayout.LayoutParams.MATCH_PARENT);
            set.constrainHeight(mFl_progress.getId() , ConstraintLayout.LayoutParams.MATCH_PARENT);

            set.applyTo((ConstraintLayout) view);

        }



        mImage_no_network = (ImageView) mFl_progress.findViewById(R.id.image_no_network);

        ImageLdHelper.loadImageCenter(view.getContext() , R.drawable.no_net_work_image , mImage_no_network);
        mFl_progress.setVisibility(View.VISIBLE);

        //没有网络就显示图片
        if (!NetWorkUtil.isNetworkConnected(view.getContext())){
            mImage_no_network.setVisibility(View.VISIBLE);
        }else {
            mImage_no_network.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏progress ， 在请求结束后，call里面 进行调用
     * */
    public void dismissProgress(){
        if (mFl_progress != null){
            mFl_progress.setVisibility(View.GONE);
        }
    }

    /**
     * 如果没有网络就显示图片，点击屏幕就 再次进行网络请求 刷新界面
     * */
    public void retry(final OnRetryListener listener ){
        mImage_no_network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage_no_network.setVisibility(View.GONE);
                if (listener != null){
                    listener.onRetry(v);
                }
            }
        });

    }

    /**
     * 如果没有网络就显示图片，或者 网络请求失败也显示图片
     * 点击就刷新界面
     * */
    public void showImage(){
        mImage_no_network.setVisibility(View.VISIBLE);
    }



    public interface OnRetryListener{
        void onRetry(View view);
    }

}
