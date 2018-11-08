package com.nacity.college.property.model;

import com.college.common_libs.domain.property.ServiceTypeTo;

import java.util.ArrayList;

/**
 * Created by xzz on 2017/9/5.
 **/

public interface RepairModel  {

    void getServiceTime(int type);

    void getUpImageToken();

    void uploadImage(ArrayList<String> pathList, String token);

    void submit(ServiceTypeTo selectServiceType, String serviceNumber, String contact, String phoneNumber, String repairTime, String serviceDescription, String repairAddress);
}
