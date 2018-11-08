package com.college.common_libs.domain.door;

import lombok.Data;

/**
 * Created by xzz on 2017/11/7.
 **/
@Data
public class DoorApplyRecordParam {

    /**
     * apartmentSid : a241b63a-df3c-43a8-a7fd-477e5f950d9f
     * ownerSid : 2470dbd5-342b-4cb4-9d99-c296397aa35a
     */

    private String apartmentSid;
    private String ownerSid;
    private int pageIndex=1;



    @Override
    public String toString() {
        return "DoorListParam{" +
                "apartmentSid='" + apartmentSid + '\'' +
                ", ownerSid='" + ownerSid + '\'' +
                ", pageIndex=" + pageIndex +
                '}';
    }
}
