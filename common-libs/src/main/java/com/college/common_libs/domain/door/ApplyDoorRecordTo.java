package com.college.common_libs.domain.door;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Created by xzz on 2017/11/8.
 */
@Data
public class ApplyDoorRecordTo implements Serializable{

    /**
     * doorApplyId : 1
     * userId : null
     * apartmentId : 1
     * roomNo : 一单元1101
     * applyStatus : 2
     * applyPassTime : 2017-10-31 13:34:05
     * applyDesc : 好，不同意
     * applyRemark : null
     * createUserId : null
     * createTime : 2017-10-31 13:34:21
     * modUserId : null
     * lastModTime : null
     * apartmentName : 幸福家园
     * userName : 超级管理员
     * applyStatusStr : 驳回
     */

    private String doorApplyId;
    private String userId;
    private String apartmentId;
    private String roomNo;
    private int applyStatus;
    private String applyPassTime;
    private String applyDesc;
    private String applyRemark;
    private String createUserId;
    private Date createTime;
    private String modUserId;
    private String lastModTime;
    private String apartmentName;
    private String userName;
    private String applyStatusStr;
    private String doorNameConcat;
    private String doorNamePassConcat;

    // 状态 转汉字
    public String getFlagStr(){

        if(applyStatus ==0) {
            return "申请中";
        }
        if(applyStatus == 1) {
            return "已同意";
        }
        if(applyStatus ==2) {
            return "已拒绝";
        }
        return "待反馈";
    }

    @Override
    public String toString() {
        return "ApplyDoorRecordTo{" +
                "doorApplyId='" + doorApplyId + '\'' +
                ", userId='" + userId + '\'' +
                ", apartmentId='" + apartmentId + '\'' +
                ", roomNo='" + roomNo + '\'' +
                ", applyStatus=" + applyStatus +
                ", applyPassTime='" + applyPassTime + '\'' +
                ", applyDesc='" + applyDesc + '\'' +
                ", applyRemark='" + applyRemark + '\'' +
                ", createUserId='" + createUserId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modUserId='" + modUserId + '\'' +
                ", lastModTime='" + lastModTime + '\'' +
                ", apartmentName='" + apartmentName + '\'' +
                ", userName='" + userName + '\'' +
                ", applyStatusStr='" + applyStatusStr + '\'' +
                '}';
    }
}
