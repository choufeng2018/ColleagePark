package com.nacity.college.property.view;

import com.nacity.college.base.BaseView;

/**
 * Created by usb on 2017/10/23.
 */

public interface ParkingView extends BaseView {
    void submit();
    void submitSuccess();
    void getTokenSuccess(String token);
}
