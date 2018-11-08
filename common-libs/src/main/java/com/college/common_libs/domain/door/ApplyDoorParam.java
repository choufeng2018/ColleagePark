package com.college.common_libs.domain.door;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by xz on 2016/6/22.
 **/
@Data
public class ApplyDoorParam  implements Serializable {

    /**
     * apartmentSid :
     * ownerSid :
     * doorIds :
     */

    private String apartmentSid;
    private String ownerSid;
    private String doorIds;



    @Override
    public String toString() {
        return "ApplyDoorParam{" +
                "apartmentSid='" + apartmentSid + '\'' +
                ", ownerSid='" + ownerSid + '\'' +
                ", doorIds='" + doorIds + '\'' +
                '}';
    }
}
