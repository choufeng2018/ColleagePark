package com.nacity.college.base;


import com.nacity.college.MainApp;
import com.nacity.college.base.info.ApartmentInfoHelper;
import com.nacity.college.base.info.UserInfoHelper;

/**
 * Created by xzz on 2017/6/16.
 **/

public class BasePresenter {
    protected UserInfoHelper userInfo= UserInfoHelper.getInstance(MainApp.mContext);
    protected ApartmentInfoHelper apartmentInfo= ApartmentInfoHelper.getInstance(MainApp.mContext);




}
