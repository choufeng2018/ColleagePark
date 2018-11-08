package com.college.common_libs.domain.door;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Created by xz on 2016/6/22.
 **/
@Data
public class UditemSheetTo implements Serializable {

    private static final long serialVersionUID = 1L;

    //id主键

    private String itemSheetSID;
    //用户SID


    private String userSid;
    //门SID


    private String doorSid;
    //0=未选中 1=已选中(是否选中)


    private int checkFlag;
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


    //把 门的实体列表 带过去
    private ApartmentDoorTo ApartmentDoor;



    @Override
    public String toString() {
        return "UditemSheetTo{" +
                "itemSheetSID='" + itemSheetSID + '\'' +
                ", userSid='" + userSid + '\'' +
                ", doorSid='" + doorSid + '\'' +
                ", checkFlag=" + checkFlag +
                ", createdOn=" + createdOn +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedOn=" + modifiedOn +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", remark='" + remark + '\'' +
                ", ApartmentDoor=" + ApartmentDoor +
                '}';
    }
}
