package com.nacity.college.property.view;

import com.nacity.college.base.BaseView;

/**
 * Created by usb on 2017/10/30.
 */

public interface DistributionOrderView extends BaseView {
    void setServiceTime(String[] dayList, String[] dateList, String[][] timeList);

    void submitSuccess(Boolean mainTo);
}
