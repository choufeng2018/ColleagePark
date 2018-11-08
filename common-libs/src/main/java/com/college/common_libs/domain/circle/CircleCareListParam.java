package com.college.common_libs.domain.circle;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/9/27.
 **/

@NoArgsConstructor
@Data
public class CircleCareListParam {

    /**
     * userId : 0
     * pageIndex : 0
     */

    private String userId;
    private int pageIndex;

    private String currentUserId;
}
