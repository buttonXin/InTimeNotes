package com.dopool.proutil.Helper;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class RxHelper {
    /**
     * 将rx从io线程转变到main线程
     * @param <T>
     * @return
     */
    public static  <T> ObservableTransformer< T ,T > ioToMain(){

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 将rx从newThread线程转变到main线程
     * @param <T>
     * @return
     */
    public static  <T> ObservableTransformer< T ,T > threadToMain(){

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 将Disposable进行isDisposed。
     */
    public static void checkDisposable(Disposable disposable){
        if (disposable != null && !disposable.isDisposed()){
            disposable.isDisposed();
        }
    }
}
