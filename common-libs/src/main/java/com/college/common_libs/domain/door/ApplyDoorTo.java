package com.college.common_libs.domain.door;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * Created by xz on 2016/6/22.
 **/
@Data
public class ApplyDoorTo implements Serializable {

    private static final long serialVersionUID = 1L;

    //id主键

    private String sheetSID;


    //审核结果描述
    private String checkDesc;

    private Integer flag;     //单据状态 0=待审 1=审核通过 2=驳回

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
    private List<UditemSheetTo> UditemSheet;




    // 状态 转汉字
    public String getFlagStr(){

        if(flag ==0) {
            return "申请中";
        }
        if(flag == 1) {
            return "已同意";
        }
        if(flag ==2) {
            return "已拒绝";
        }
        return "待反馈";
    }



    @Override
    public String toString() {
        return "ApplyDoorTo{" +
                "sheetSID='" + sheetSID + '\'' +
                ", checkDesc='" + checkDesc + '\'' +
                ", flag=" + flag +
                ", createdOn=" + createdOn +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedOn=" + modifiedOn +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", remark='" + remark + '\'' +
                ", UditemSheet=" + UditemSheet +
                '}';
    }
}
