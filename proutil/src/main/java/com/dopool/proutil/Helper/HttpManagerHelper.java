package com.dopool.proutil.Helper;


import com.dopool.proutil.BaseApi.BaseInterface;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/8/1 0001.
 * 网络请求的管理类 ， 用来获取retrofit实例，然后进行网络请求，
 * 一些网络的设置就在这里进行初始化配置
 */

public class HttpManagerHelper {

    private static HttpManagerHelper mHttpManager = null ;

    private OkHttpClient mOkHttpClient;

    public BaseInterface mAPIInterface;
    private Retrofit mRetrofit;

    //私有化
    private HttpManagerHelper(){}

    private HttpManagerHelper(OkHttpClient okHttpClient , String url){

        if (okHttpClient == null){
            mOkHttpClient = new OkHttpClient();
        }else {
            mOkHttpClient = okHttpClient ;
        }

        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();


    }

    public static HttpManagerHelper getInstance(){
        return init(null , "");
    }

    public static HttpManagerHelper init(OkHttpClient okHttpClient , String url) {

        if (mHttpManager == null){
            synchronized (HttpManagerHelper.class){
                if (mHttpManager == null){
                    mHttpManager = new HttpManagerHelper(okHttpClient , url);
                }
            }
        }
        return mHttpManager ;
    }

    /**
     *直接调用这个就行了
     */
    public static BaseInterface getAPIInterface(){

        return getInstance().mAPIInterface;
    }

    public <T> T create(final Class<T> service){

        return mRetrofit.create(service);
    }


}
