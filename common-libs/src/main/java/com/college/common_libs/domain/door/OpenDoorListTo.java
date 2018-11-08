package com.college.common_libs.domain.door;

import lombok.Data;

/**
 * Created by xzz on 2017/11/7.
 **/
@Data
public class OpenDoorListTo {

    /**
     * doorId : 1
     * doorName : 北门
     * deviceId : 5898658994520064
     * deviceModel : A1000
     * devicePid : A08EA40826B0E
     * deviceRegisterId : 10018
     * supplierId : 1
     * supplierName : 令令门禁
     * openDoorCount : null
     * lastOpenTime : null
     */

    private String doorId;
    private String doorName;
    private String deviceId;
    private String deviceModel;
    private String devicePid;
    private String deviceRegisterId;
    private String supplierId;
    private String supplierName;
    private int openDoorCount;
    private String lastOpenTime;
    private String sdkKey;
    private String miaodouKey;
    private String miaodouApartmentId;
    private String lingLingId;



    @Override
    public String toString() {
        return "OpenDoorListTo{" +
                "doorId='" + doorId + '\'' +
                ", doorName='" + doorName + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", devicePid='" + devicePid + '\'' +
                ", deviceRegisterId='" + deviceRegisterId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", openDoorCount=" + openDoorCount +
                ", lastOpenTime='" + lastOpenTime + '\'' +
                '}';
    }
}
