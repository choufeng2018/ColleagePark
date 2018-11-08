package com.nacity.college.property.model;

import com.college.common_libs.domain.property.ServiceTypeTo;
import com.college.common_libs.domain.property.WaterDistributionTo;

import java.util.List;

/**
 *  Created by usb on 2017/10/30.
 */

public interface DistributionOrderModel {
    void getServiceTime(int type);
    void submit(ServiceTypeTo selectServiceType, String serviceNumber, String contact, String phoneNumber, String repairTime, String serviceDescription, String repairAddress, List<WaterDistributionTo> serviceTypeIdList);


}
