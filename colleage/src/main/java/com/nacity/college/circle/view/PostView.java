package com.nacity.college.circle.view;

import com.nacity.college.base.BaseView;

/**
 * Created by xzz on 2017/7/1.
 **/

public interface PostView extends BaseView {
    void showWarn(String message);

    void enterLifeActivity();

    void addPost();
}
