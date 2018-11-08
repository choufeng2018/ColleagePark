package com.college.common_libs.domain.user;

import com.college.common_libs.domain.apartment.ApartmentInfoTo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

@Data
public class UserInfoTo
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  private BigDecimal accountBalance;
  private String apartmentSid;
  private String appPartner;
  private String babyTag;
  private BigDecimal buildingArea;
  private String buildingType;
  private String carTag;
  private String chargeEndDate;
  private String chargeOverdue;
  private String chargeStartDate;
  private Timestamp createdOn;
  private String createdby;
  private BigDecimal creditScore;
  private String deviceToken;
  private String familyName;
  private String familyType;
  private String homepageImage;
  private String inputDate;
  private BigDecimal lateFee;
  private Timestamp modifiedOn;
  private String modifiedby;
  private String neighborNotification;
  private String noticeNotification;
  private String olderTag;
  private String birthday;
  private String image;
  private String name;
  private String no;
  private String phone;
  private int status;
  private String tag;
  private String type;
  private String petTag;
  private String purchaseNotification;
  private String remark;
  private BigDecimal unitPrice;
  private String verificationTag;


  //-----------------------------//
  private String dashboardTag;
  private String apartmentTag;
  private int category;
  private String address;

  private String apartmentName;

  private String userId;

  private String userCode;

  private String userAccount;

  private String userName;

  private String password;

  private int userType;

  private Long companyId;

  private String userMobile;

  private String userTel;

  private String userEmail;

  private String departmentId;

  private String userMemo;

  private String userStatus;

  private String isdel;

  private Date createTime;

  private Date lastModTime;

  private Long createUserId;

  private Long modUserId;

  private String regGardenId;

  private String nickname;

  private String sex;

  private String updateGardenId;

  private ApartmentInfoTo commonGarden;

  private String realName;

  private String userBirth;

  private String userPic;

  private String roomNo;
}
