package com.nacity.college.property.view;

import com.college.common_libs.domain.property.ParkingApplyTo;
import com.nacity.college.base.BaseView;

/**
 *  Created by usb on 2017/10/23.
 */

public interface ParkingDetailView extends BaseView {
    void getParkingDetail(ParkingApplyTo parkingApplyTo);
}
