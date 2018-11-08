package com.college.common_libs.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/9/27.
 **/

@NoArgsConstructor
@Data
public class MainMenuParam {

    /**
     * userId : 0
     * gardenId : 1
     * type : 1
     */

    private String userId;
    private String gardenId;
    private String type;
}
