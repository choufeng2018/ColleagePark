package com.college.common_libs.domain.door;

import lombok.Data;

/**
 * Created by xzz on 2017/11/8.
 **/
@Data
public class CanApplyDoorTo {

    /**
     * doorId : 5817525722611712
     * apartmentId : 5864435471155221
     * apartmentName : 江滨花园
     * doorName : 1幢1单元楼门
     * doorTypeStr : 大门
     * doorStatusStr : 正常
     */

    private String doorId;
    private String apartmentId;
    private String apartmentName;
    private String doorName;
    private String doorTypeStr;
    private String doorStatusStr;
    private boolean select;


    @Override
    public String toString() {
        return "CanApplyDoorTo{" +
                "doorId='" + doorId + '\'' +
                ", apartmentId='" + apartmentId + '\'' +
                ", apartmentName='" + apartmentName + '\'' +
                ", doorName='" + doorName + '\'' +
                ", doorTypeStr='" + doorTypeStr + '\'' +
                ", doorStatusStr='" + doorStatusStr + '\'' +
                '}';
    }
}
