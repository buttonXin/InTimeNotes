package com.dopool.proutil.base;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by oldhigh on 2017/11/21.
 */

public abstract class BaseObserver<T> implements Observer<T> {



    /**成功的回调*/
    public abstract void onSuccess(T t);

    /**失败的回调*/
    public abstract void onFail(Throwable t);




    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFail(e);
    }

    @Override
    public void onComplete() {

    }
}
