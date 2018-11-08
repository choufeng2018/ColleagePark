package com.nacity.college.circle;

/**
 * Created by xzz on 2017/6/28.
 **/

public interface BaseCallback {

    void message(String message);

    void error(Throwable error);
}
