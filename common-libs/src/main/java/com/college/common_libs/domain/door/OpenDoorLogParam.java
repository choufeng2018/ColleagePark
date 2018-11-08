package com.college.common_libs.domain.door;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by xz on 2016/6/24.
 **/
@Data
public class OpenDoorLogParam implements Serializable{


    /**
     * apartmentSid :
     * ownerSid :
     * doorId :
     * supplierId :
     * logType :
     * isOpenSuccess :
     * openDoorTime :
     * callImg :
     * openRemark :
     * openContentDesc :
     * callContinue : 0
     * callTime :
     */

    private String apartmentSid;
    private String ownerSid;
    private String doorId;
    private String supplierId;
    private String logType;
    private int isOpenSuccess;
    private String openDoorTime;
    private String callImg;
    private String openRemark;
    private String openContentDesc;
    private int callContinue;
    private String callTime;



    @Override
    public String toString() {
        return "OpenDoorLogParam{" +
                "apartmentSid='" + apartmentSid + '\'' +
                ", ownerSid='" + ownerSid + '\'' +
                ", doorId='" + doorId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", logType='" + logType + '\'' +
                ", isOpenSuccess='" + isOpenSuccess + '\'' +
                ", openDoorTime='" + openDoorTime + '\'' +
                ", callImg='" + callImg + '\'' +
                ", openRemark='" + openRemark + '\'' +
                ", openContentDesc='" + openContentDesc + '\'' +
                ", callContinue=" + callContinue +
                ", callTime='" + callTime + '\'' +
                '}';
    }
}