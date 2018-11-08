package com.nacity.college.base.info;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.college.common_libs.domain.user.UserInfoTo;


/**
 * Created by Admin on 2015-03-05
 */
public class UserInfoHelper {

    protected static UserInfoHelper instance;
    private UserInfoTo userInfoTo;
    private Context mContext;

    private UserInfoHelper(Context context) {
         mContext = context;
        load(context);
    }


    public static UserInfoHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (UserInfoHelper.class) {
                if (instance == null) {
                    instance = new UserInfoHelper(context);
                }
            }
        }
        instance.mContext = context;
        return instance;
    }

    public UserInfoTo getUserInfoTo() {
        return userInfoTo;
    }

    public String getSid() {

        return userInfoTo == null ? "" : userInfoTo.getUserId();
    }

    public String getPhone() {
        if (userInfoTo == null) return "";
        if (!TextUtils.isEmpty(userInfoTo.getUserMobile())) {

            return userInfoTo.getPhone();
        }


        return "";
    }

    public void updateUser(UserInfoTo infoTo) {
        userInfoTo = infoTo;
        save();
    }

    private void save() {
        ConfigUtil.saveString(PreferenceManager.getDefaultSharedPreferences(mContext),
                KeyValue.KEY_USER_INFO, JSON.toJSONString(userInfoTo));
    }

    public void load(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String userJson = ConfigUtil.getString(sp, KeyValue.KEY_USER_INFO, "");
        Log.d("KeyValue-----", userJson);
        if (!TextUtils.isEmpty(userJson)) {
            userInfoTo = JSON.parseObject(userJson, UserInfoTo.class);
        }
    }

    public boolean isLogin(){
     SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
      return  ConfigUtil.getBoolean(sp, KeyValue.KEY_IS_LOGIN_INFO);
    }
    public void updateLogin(boolean flag){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        ConfigUtil.saveBoolean(sp , KeyValue.KEY_IS_LOGIN_INFO,flag);
    }
}
