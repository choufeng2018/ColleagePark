package com.college.common_libs.domain.apartment;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/9/19.
 **/

@NoArgsConstructor
@Data
public class AdvertiseParam  {


    private String pageNumber="1";
    private String pageSize="20";
    private int adGardenStatus=1;
    private int adType;
    private String adSubject;
    private int adPublicStatus=1;
    private String gardenName;
    private String gardenId;
}
