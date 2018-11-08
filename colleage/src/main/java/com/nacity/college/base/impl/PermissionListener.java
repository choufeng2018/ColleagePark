package com.nacity.college.base.impl;

/**
 * Created by xzz on 2017/6/27.
 **/

public interface PermissionListener {

    void accept(String permission);

    void refuse(String permission);
}
