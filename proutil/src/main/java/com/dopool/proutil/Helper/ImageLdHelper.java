package com.dopool.proutil.Helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.dopool.proutil.R;
import com.dopool.proutil.util.ScreenUtil;


/**
 * 这里面默认的图片需要在进行配置，现在还没有搞好呢
 * Created by Administrator on 2017/8/2 0002.
 */

public class ImageLdHelper {


    private static RequestOptions getOptionsNot_placeholder(){
        return  new RequestOptions()
                .centerCrop()
//                .error(R.mipmap.ic_launcher)
                .priority(Priority.HIGH)
                //    .diskCacheStrategy(DiskCacheStrategy.NONE)
                ;
    }

    private static RequestOptions getOptions(){
        return  new RequestOptions()
                 .centerCrop()
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
                .priority(Priority.HIGH)
                //    .diskCacheStrategy(DiskCacheStrategy.NONE)
                ;
    }
    private static RequestOptions getOptionsCircle(){
        return  new RequestOptions()
                .circleCrop()
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
                .priority(Priority.HIGH)
                ;
    }
    private static RequestOptions getOptionsRound(int roundingRadius){
        return  new RequestOptions()
                .transform(new RoundedCorners(roundingRadius))
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
                .priority(Priority.HIGH)
                ;
    }

    /**
     * 加载图片，图片显示模式正常，或者是image的的模式
     */
    public static void loadImage(Context context , Object url , ImageView imageView){

        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    /**
     * 加载url的图片,图片中间显示模式
     */
    public static void loadImageCenter(Context context , Object url , ImageView imageView){
        Glide.with(context)
                .load(url)
                .apply(getOptions())
            //    .transition(new DrawableTransitionOptions().crossFade(1500))
             //   .listener(mListener)
                //缩略图，也可以使用        .thumbnail( 0.1f  or Glide.with(context).load(url))
                .into(imageView);
    }

    /**
     * 这个是没有占位符
     */
    public static void loadImageNotPlaceHolder(Context context , Object url , ImageView imageView){
        Glide.with(context)
                .load(url)
                .apply(getOptionsNot_placeholder())
                //    .transition(new DrawableTransitionOptions().crossFade(1500))
             //   .listener(mListener)
                //缩略图，也可以使用        .thumbnail( 0.1f  or Glide.with(context).load(url))
                .into(imageView);
    }

    /**
     *这是加载成圆形的图片
     */
    public static void loadImageCircle(Context context , Object url , ImageView imageView){
        Glide.with(context)
                .load(url)
                .apply(getOptionsCircle())
            //    .listener(mListener)
                .into(imageView);
    }

    /**
     *
     * @param roundingRadius 这是设置圆角的大小
     */
    public static void loadImageRound(Context context , Object resId ,
                                      int roundingRadius , ImageView imageView){
        Glide.with(context)
                .load(resId)
                .apply(getOptionsRound(ScreenUtil.convertDipToPx(context , roundingRadius)))
             //   .listener(mListener)
                .into(imageView);
    }



    // 下载图片的log监听
    private  static RequestListener<Drawable> mListener = new RequestListener<Drawable>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            Log.d("glide","load image fail !!! " + e.getMessage());
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            Log.d("glide" , "load image success " + resource + "  object --> " + model);
            return false;
        }
    };

}
