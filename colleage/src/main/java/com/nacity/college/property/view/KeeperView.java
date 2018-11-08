package com.nacity.college.property.view;

import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.base.BaseView;

import java.util.List;

/**
 * Created by xzz on 2017/9/7.
 **/

public interface KeeperView extends BaseView {
    void getServiceTypeSuccess(List<ServiceTypeTo> serviceList);
}
