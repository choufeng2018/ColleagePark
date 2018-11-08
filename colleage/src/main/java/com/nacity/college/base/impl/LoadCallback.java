package com.nacity.college.base.impl;

/**
 * Created by xzz on 2017/9/6.
 **/

public interface LoadCallback<T> {

    void success(T t);

    void fail(Throwable e);

    void showErrorMessage(String message);
}
