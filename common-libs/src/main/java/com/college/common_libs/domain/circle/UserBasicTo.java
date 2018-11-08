 package com.college.common_libs.domain.circle;
import java.io.Serializable;

import lombok.Data;

 @Data
public class UserBasicTo
  implements Serializable
{
  private String sid;
  private String name;
  private String icon;
  private String phone;
  private String type;
 }

