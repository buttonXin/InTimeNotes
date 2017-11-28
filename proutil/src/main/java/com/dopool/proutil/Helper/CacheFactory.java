package com.dopool.proutil.Helper;

import com.dopool.proutil.BaseApplication;
import com.dopool.proutil.util.NetWorkUtil;


import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by oldhigh on 2017/11/19.
 */

public class CacheFactory {


    /**
     * 缓存机制
     * 在响应请求之后在 data/data/<包名>/cache 下建立一个response 文件夹，保持缓存数据。
     * 这样我们就可以在请求的时候，如果判断到没有网络，自动读取缓存的数据。
     * 同样这也可以实现，在我们没有网络的情况下，重新打开App可以浏览的之前显示过的内容。
     * 也就是：判断网络，有网络，则从网络获取，并保存到缓存中，无网络，则从缓存中获取。
     */
    public static final Interceptor cacheIntercetor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();

            if (!NetWorkUtil.isNetworkConnected(BaseApplication.mAppContext)){
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }

            Response originalResponse = chain.proceed(request);



            if (NetWorkUtil.isNetworkConnected(BaseApplication.mAppContext)){

                String  cacheControl = request.cacheControl().toString();

                return  originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Pragma")
                        .build();
            }else {
                // 无网络时 设置超时为1周
                int maxStale = 60 * 60 * 24 * 7;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();

            }

        }
    };

    // 指定缓存路径,缓存大小 50Mb
    public static final Cache cache = new Cache(
            new File(BaseApplication.mAppContext.getCacheDir(), "HttpCache"),
            1024 * 1024 * 50);



}
