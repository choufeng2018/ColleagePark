package com.college.common_libs.domain.property;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Created by usb on 2017/10/23.
 */
@NoArgsConstructor
@Data
public class ParkingApplyListParam {
    private int nextPage;
    private int limit;
    private String userId;
    private String gardenId;
}
