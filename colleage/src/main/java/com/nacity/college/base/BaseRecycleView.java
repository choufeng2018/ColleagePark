package com.nacity.college.base;

import java.util.List;


/**
 * Created by xzz on 2017/6/17.
 **/

public interface BaseRecycleView<T> {

    void refreshRecycleView(List<T> list);

    void showMessage(String message);

    void loadError(Throwable e);





}
