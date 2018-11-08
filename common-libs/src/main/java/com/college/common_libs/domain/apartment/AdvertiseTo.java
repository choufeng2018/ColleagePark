package com.college.common_libs.domain.apartment;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/9/19.
 **/

@NoArgsConstructor
@Data
public class AdvertiseTo implements Serializable {



    private String adGardenId;
    private String adPublicId;
    private int adGardenStatus;
    private String gardenId;
    private String gardenName;
    private int adIndex;
    private String startDate;
    private String endDate;
    private String createdOn;
    private String createdby;
    private String modifiedOn;
    private String modifiedby;
    private int adType;
    private String adImage;
    private String adSubject;
    private int adStatus;
    private String adUrl;
    private String adContent;
}
