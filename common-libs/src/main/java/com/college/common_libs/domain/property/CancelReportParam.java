package com.college.common_libs.domain.property;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/10/11.
 **/

@NoArgsConstructor
@Data
public class CancelReportParam {

    /**
     * serviceId : 0
     * loginUserId : 0
     */

    private String serviceId;
    private String loginUserId;
}
