package com.nacity.college.base;

/**
 * Created by xzz on 2017/8/25.
 **/

public interface BaseView<T>  {

    void loadingShow();


    void loadingDismiss();


    void loadError(Throwable e);

    void showMessage(String message);

    void showErrorMessage(String message);

    void getDataSuccess(T data);

void submitDataSuccess(Object data);
}
