package com.nacity.college.base.utils;

import android.content.Context;
import android.text.TextUtils;

import es.dmoral.toasty.Toasty;

/**
 * Created by xzz on 2017/9/5.
 **/

public class CheckPhoneUtil  {
    public static boolean checkPhoneNumber(String phoneNumber, Context context){
        if (TextUtils.isEmpty(phoneNumber)) {
            Toasty.warning(context, "联系电话不能为空，请完善后提交").show();
            return true;
        }else if (phoneNumber.length() != 11 || !phoneNumber.trim().matches("^(1[3|4|5|8|7]\\d{9})$")) {
            Toasty.warning(context,  "请输入正确的手机号码").show();
            return true;
        }
        return false;
    }
}
