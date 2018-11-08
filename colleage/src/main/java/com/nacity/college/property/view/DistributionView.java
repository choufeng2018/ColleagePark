package com.nacity.college.property.view;

import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.base.BaseView;

import java.util.List;

/**
 *  Created by usb on 2017/10/26.
 */

public interface DistributionView extends BaseView {
    void getServiceTypeSuccess(List<ServiceTypeTo> serviceList);
    void getAdSuccess(List<AdvertiseTo> advertiseList);

}
