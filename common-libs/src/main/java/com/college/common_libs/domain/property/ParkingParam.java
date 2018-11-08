package com.college.common_libs.domain.property;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Created by usb on 2017/10/23.
 */
@NoArgsConstructor
@Data
public class ParkingParam {
    private String gardenId;
    private String userId;
    private String userName;
    private String carNo;
    private String driverImgs;
    private String loadType;
    private String loadArea;
    private String applyDead;
    private String otherDesc;

}
