package com.nacity.college.base.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by xzz on 2017/9/17.
 **/

public class SoftKeyBoardUtil {
    public static void hideInputWindow(Activity activity) {

        final View v = activity.getWindow().peekDecorView();
        if (v != null && v.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager)activity. getSystemService(activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
