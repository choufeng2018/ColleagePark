package com.nacity.college.common.model;

/**
 * Created by xzz on 2017/9/29.
 **/

public interface UserInfoModel {

    void getUserInfo();
    void logOut(String userId);

    void getUserFans(String userId);
}
