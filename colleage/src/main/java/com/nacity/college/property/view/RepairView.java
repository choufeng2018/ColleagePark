package com.nacity.college.property.view;

import com.nacity.college.base.BaseView;


/**
 * Created by xzz on 2017/9/5.
 **/

public interface RepairView extends BaseView {

    void setServiceTime(String[] dayList, String[] dateList, String[][] timeList);

    void getTokenSuccess(String token);

    void submit();

    void submitSuccess(Boolean mainTo);
}
