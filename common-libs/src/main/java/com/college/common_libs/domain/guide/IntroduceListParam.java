package com.college.common_libs.domain.guide;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/9/22.
 **/

@NoArgsConstructor
@Data
public class IntroduceListParam  {
    private String nextPage="1";
    private String limit="20";
}
