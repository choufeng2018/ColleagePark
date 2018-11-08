package com.college.common_libs.domain.property;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/10/11.
 **/

@NoArgsConstructor
@Data
public class ServiceRecordParam {


    private int pageIndex;
    private String loginUserId;
    private String categoryType;
    private String gardenId;
}
