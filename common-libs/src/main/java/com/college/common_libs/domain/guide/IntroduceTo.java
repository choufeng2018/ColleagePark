package com.college.common_libs.domain.guide;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xz on 2017/2/28.
 **/
@NoArgsConstructor
@Data
public class IntroduceTo {


    /**
     * summary : 123
     * lastModTime : 2017-09-21 15:58:46
     * browseTimes : 0
     * isTop : 1
     * id : 357950245885181952
     * title : 介绍
     * userName : 超级管理员
     * status : 1
     */

    private String summary;
    private String lastModTime;
    private int browseTimes;
    private int isTop;
    private String id;
    private String title;
    private String userName;
    private int status;
}
