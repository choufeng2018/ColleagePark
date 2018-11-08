package com.nacity.college.base.impl;

import android.view.View;

/**
 * Created by xzz on 2017/6/27.
 **/

public interface FragmentPermissionListener {

    void accept(String permission, View view);

    void refuse(String permission);
}
