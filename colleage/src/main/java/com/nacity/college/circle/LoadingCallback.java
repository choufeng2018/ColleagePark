package com.nacity.college.circle;

/**
 * Created by xzz on 2017/6/16.
 */

public interface LoadingCallback<T> {
    void success(T apartmentTo);

    void message(String message);

    void error(Throwable error);
}
