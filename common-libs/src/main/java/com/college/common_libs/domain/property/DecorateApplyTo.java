package com.college.common_libs.domain.property;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Created by usb on 2017/10/17.
 */
@NoArgsConstructor
@Data
public class
DecorateApplyTo implements Serializable {
    private String  decorateId; //true //string 主键id
    private String  gardenId; //true //string 项目ID
    private String  userName; //true //string 姓名
    private String  userTel;// true string 联系电话
    private String companyName;// true string 公司名称
    private String roomId;// true string 房号Id
    private String havePicture;// true number 有无施工图 1:有 2:无
    private String explains;// true //string 说明
    private String applyStatus;// true number 状态 1:申请中 2:申请通过 3:申请被拒
    private String answerContent ;//true string 回复内容
    private String remark;// true;// string 备注
    private String roomNoDesc;// 房号全称
    private String createTime;// true string 创建时间
    private String lastModTime;// true string 更新时间
    private String createUserId;// true string 创建人
    private String modUserId;// true string 更新人
    private String corporationImgs;// true string 法人图片
    private String businessImgs;// true string 营业执照图片
    private String contractImgs;// true string 房屋租赁合同图片
    private String designerImgs;// true string 装修设计方案图片
    private String gardenName;// true string 项目名称
    private String userMobile;// true string 申请账号
    private String roomNo;// true string 房号
    private String havePictureDesc;// true string 是否有施工图描述
    private String  applyStatusDesc;// true string 状态描述
    private String corporationImgList;// true string
    private String businessImgList;// true string
    private String contractImgList;// true string
    private String designerImgList;// true string
    private String answerTime;// true string

}
