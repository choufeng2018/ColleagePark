package com.college.common_libs.domain.door;

import lombok.Data;

/**
 * Created by xzz on 2017/11/3.
 */
@Data
public class RemoteDoorMessageTo {

    /**
     * statusCode : 30016
     * methodName : remoteOpenDoor
     * responseResult : 设备不在线
     */

    private String statusCode;
    private String methodName;
    private String responseResult;


}
