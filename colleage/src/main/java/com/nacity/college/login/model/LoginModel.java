package com.nacity.college.login.model;

import android.widget.TextView;

/**
 * Created by xzz on 2017/9/17.
 **/

public interface LoginModel {

    void getVerificationCode(String type, String phoneNumber, TextView getVerificationCode);

    void upLoadDevice(String userId);
    void parkRegister(String cityName, String parkSid, String phoneNumber, String verificationCode);

    void login(String phoneNumber, String verificationCode);
}
