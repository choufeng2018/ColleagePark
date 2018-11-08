package com.nacity.college.property.view;

import com.college.common_libs.domain.property.ServiceMainTo;
import com.nacity.college.base.BaseView;

/**
 * Created by xzz on 2017/10/11.
 **/

public interface ServiceDetailView extends BaseView {

   void  getServiceDetailSuccess(ServiceMainTo mainTo);

    void cancelReportSuccess();
}
