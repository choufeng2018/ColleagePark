package com.college.common_libs.domain.guide;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/9/22.
 **/

@NoArgsConstructor
@Data
public class EnterpriseListParam {
    private int pageIndex;
    private String gardenId;
    private String team;
    private String building;
    private String unit;
    private String roomNo;
    private String fullAddress;
}
