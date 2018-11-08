package com.college.common_libs.domain.property;
import java.io.Serializable;
import java.util.List;

import lombok.Data;


@Data
 public class ServiceTimeTo implements Serializable
 {
   private String day;
   private String dateName;
   private List<String> times;
 }
