package com.college.common_libs.domain.circle;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/9/24.
 **/

@NoArgsConstructor
@Data
public class CirclePostListParam {


    /**
     * pageIndex : 0
     * typeId : 0
     * createUserId : 0
     */

    private int pageIndex;
    private String typeId;
    private String createUserId;
    private String currentUserId;
}
