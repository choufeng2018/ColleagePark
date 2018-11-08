package com.college.common_libs.domain.circle;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/9/27.
 **/

@NoArgsConstructor
@Data
public class FansListParam {


    private String followUserId;
    private String currentUserId;
    private int pageIndex;
}
