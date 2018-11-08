package com.college.common_libs.domain.property;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/10/12.
 **/

@NoArgsConstructor
@Data
public class EvaluateServiceParam {


    private String serviceId;
    private int evaluateServiceAttitude;
    private int evaluateFinishSpeed;
    private int evaluatePleasedDegree;
    private String serviceDesc;
    private String loginUserId;
}
