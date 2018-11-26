package com.college.common_libs.domain.property;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Created by usb on 2017/10/23.
 */
@NoArgsConstructor
@Data
public class ParkingApplyTo {

    /**
     * parkId : 5813514311958528
     * gardenId : 355396305909972994
     * gardenName : 幸福家园
     * userName : 蒋
     * carNo : 浙A12344
     * loadType : 2
     * loadTypeDesc : 平面
     * loadArea : 西区-车库212
     * applyDead : 1个月
     * otherDesc : 82842498374293
     * applyStatus : 1
     * applyStatusDesc : 申请中
     * answerContent : null
     * remark : null
     * createTime : 2017-10-23 16:53:08
     * lastModTime : null
     * createUserId : 359023884197953536
     * userMobile : 18300675657
     * modUserId : null
     * driverImgs : http://picjoy.joyhomenet.com/Fv1L5c9fuepLgbEhsJ9CnOhTii56,http://picjoy.joyhomenet.com/Fv5_JLptKwvv3Sd-8QC0pv8Ch0sd
     * driverImgList : null
     */
    private String parkId;
    private String answerTime;
    private String gardenId;
    private String gardenName;
    private String userName;
    private String carNo;
    private String    loadType;
    private String loadTypeDesc;
    private String loadArea;
    private String applyDead;
    private String otherDesc;
    private String    applyStatus;
    private String applyStatusDesc;
    private String answerContent;
    private String remark;
    private String createTime;
    private String lastModTime;
    private String createUserId;
    private String userMobile;
    private String modUserId;
    private String driverImgs;
    private String driverImgList;

}
