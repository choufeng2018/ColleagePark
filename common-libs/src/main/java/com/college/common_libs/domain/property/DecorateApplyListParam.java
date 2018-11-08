package com.college.common_libs.domain.property;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Created by usb on 2017/10/17.
 */
@NoArgsConstructor
@Data
public class DecorateApplyListParam {
    private int pageIndex;
    private String userId;
    private String gardenId;
}
