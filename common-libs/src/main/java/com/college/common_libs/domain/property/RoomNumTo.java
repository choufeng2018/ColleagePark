package com.college.common_libs.domain.property;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Created by usb on 2017/10/18.
 */
@NoArgsConstructor
@Data
public class RoomNumTo implements Serializable {


    /**
     * id : 2
     * createUserId : null
     * createTime : null
     * modUserId : null
     * lastModTime : null
     * gardenId : 355396305909972993
     * team : null
     * building : A座
     * unit : 2单元
     * roomNo : 704
     * username : null
     * phone : null
     * joinTime : null
     * remark : null
     * isdel : null
     * fullAddress : null
     * gardenName : 联合大厦
     * idStr : null
     * gardenIdStr : null
     */

    private String id;
    private String createUserId;
    private String createTime;
    private String modUserId;
    private String lastModTime;
    private String gardenId;
    private String team;
    private String building;
    private String unit;
    private String roomNo;
    private String username;
    private String phone;
    private String joinTime;
    private String roomNoDesc;
    private String remark;
    private String isdel;
    private String fullAddress;
    private String gardenName;
    private String idStr;
    private String gardenIdStr;

}
