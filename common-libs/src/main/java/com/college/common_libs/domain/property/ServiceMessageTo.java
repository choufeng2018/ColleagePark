/*    */ package com.college.common_libs.domain.property;
/*    */ 
/*    */


import com.college.common_libs.domain.user.UserInfoTo;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;


@Data
public class ServiceMessageTo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String messageSid;
  private String serviceSid;
  private int messageType;
  private String serviceMessage;
  private Timestamp createdOn;
  private String createdBy;
  private String tag;
  private UserInfoTo userBasic;



}

