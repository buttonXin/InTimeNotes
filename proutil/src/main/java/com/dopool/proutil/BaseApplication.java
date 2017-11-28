package com.dopool.proutil;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.dopool.proutil.Helper.HttpManagerHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 基础application类，需要进行继承，在使用
 * Created by oldhigh on 2017/11/24.
 */

public abstract class BaseApplication extends Application {

    //是否开启debug
    public boolean isDebug  = true;

    //log的名字
    private String logName = "LOGGER->";

    public   static Context mAppContext;

    //基础的url
    public String mBaseUrl = "https://dev-apimid.dopool.com";

    /**
     * 配置信息
     * @param isDebug 是否需要打印log ， 在上线的时候需要改成flase ，默认为true
     * @param logName 打印log的名字，可以不管 默认为 "LOGGER->"
     * @param baseUrl 基础的接口域名 默认为 https://dev-apimid.dopool.com
     *
     *                在继承的时候 使用如下 ，否则AndroidManifest.xml 不能用
     *                 public MyApp(){
     *                       super(Constant.IS_DEV , "test->" , Constant.BASE_URL);
                        }
     */
    public BaseApplication(boolean isDebug, String logName , String baseUrl) {
        this.isDebug = isDebug;
        this.logName = logName;
        mBaseUrl = baseUrl ;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppContext = getApplicationContext();

        if (isDebug){
            configLogger(logName);
        }

        configHttp();

    }


    /**
     * logger的配置
     * @param logName 设置显示的名字
     */
    protected void configLogger(String logName){
        //logger
        Logger.addLogAdapter(new AndroidLogAdapter(
                PrettyFormatStrategy.newBuilder()
                        .tag("".equals(logName)? "LOGGER->" : logName)
                        .build()));
    }

    /**
     * 网络配置
     */

    public void configHttp(){
        //网络请求的 log 配置
        HttpLoggingInterceptor.Level level ;
        if (isDebug){
            level= HttpLoggingInterceptor.Level.BODY;
        }else {
            level= HttpLoggingInterceptor.Level.NONE;
        }
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("Http-Info-->" , message);
            }
        });
        loggingInterceptor.setLevel(level);
        //okhttp 配置
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                //失败重新请求
                .retryOnConnectionFailure(true)
                .build();
        HttpManagerHelper.init(okHttpClient , mBaseUrl);
    }






}
