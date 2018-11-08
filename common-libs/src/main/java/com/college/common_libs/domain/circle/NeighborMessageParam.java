package com.college.common_libs.domain.circle;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;


@Data
public class NeighborMessageParam implements Serializable
{

  private String apartmentSid;
  private String userSid;
  private Date lastTime;
  private int pageIndex;
 }

