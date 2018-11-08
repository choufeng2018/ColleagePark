package com.college.common_libs.domain.guide;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/9/22.
 **/

@NoArgsConstructor
@Data
public class EnterpriseTo implements Serializable{

    private static final long serialVersionUID = 1L;


    private String id;
    private String createUserId;
    private String createTime;
    private String modUserId;
    private String lastModTime;
    private String gardenId;
    private String logoPic;
    private String homePic;
    private String name;
    private String linkman;
    private String phone;
    private String companyDesc;
    private String roomId;
    private int status;
    private int isTop;
    private String topTime;
    private String isdel;
    private String team;
    private String building;
    private String unit;
    private String roomNo;
    private String fullAddress;
    private String gardenName;
    private String idStr;
    private String roomIdStr;
    private String gardenIdStr;
}
