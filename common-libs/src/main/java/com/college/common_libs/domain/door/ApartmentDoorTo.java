package com.college.common_libs.domain.door;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * Created by xz on 2016/6/22.
 **/
@Data
public class ApartmentDoorTo implements Serializable {

    private static final long serialVersionUID = 1L;

    //id主键
    private String doorSid;

    //门名称
    private String doorName;

    //小区SID
    private String apartmentSid;

    //门状态 0=禁用  1=正常
    private String doorType;

    //门类型  0=大门 1=楼门
    private String doorFlag;

    //创建时间
    private Date createdOn;

    //创建用户SID
    private String createdBy;

    //修改时间
    private Date modifiedOn;

    //修改用户SID
    private String modifiedBy;

    //备注
    private String remark;
    private String status;


    //第三方 开门 token （带有日期校验）
    private List<UserTokenInfoTo> userTokenList;


    @Override
    public String toString() {
        return "ApartmentDoorTo{" +
                "doorSid='" + doorSid + '\'' +
                ", doorName='" + doorName + '\'' +
                ", apartmentSid='" + apartmentSid + '\'' +
                ", doorType='" + doorType + '\'' +
                ", doorFlag='" + doorFlag + '\'' +
                ", createdOn=" + createdOn +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedOn=" + modifiedOn +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", remark='" + remark + '\'' +
                ", status='" + status + '\'' +
                ", userTokenList=" + userTokenList +
                '}';
    }
}
