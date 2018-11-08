package com.nacity.college.base;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.nacity.college.login.SelectCityActivity;
import com.nacity.college.property.SelectServiceCategoryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xzz on 2017/8/31.
 **/

public class ActivityUitl  {
    @SuppressLint("StaticFieldLeak")
    public static SelectCityActivity selectCityActivity=null;

    public static List<SelectServiceCategoryActivity>categoryActivitieList=new ArrayList<>();

    public static List<Activity>activityList=new ArrayList<>();
}
