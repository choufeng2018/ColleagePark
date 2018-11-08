package com.nacity.college.base;

import rx.Observer;

/**
 * Created by xzz on 2017/8/26.
 **/

public  abstract class MyObserver<T> implements Observer<T> {


    @Override
    public void onCompleted() {
        System.out.println("---");
    }

    @Override
    public void onError(Throwable e) {
        System.out.println(e+"----");
    }
}
