 package com.college.common_libs.domain.rent;

 import java.io.Serializable;

 import lombok.Data;
 import lombok.NoArgsConstructor;

 @NoArgsConstructor
 @Data
 public class HouseListParam implements Serializable {
     private String pageNumber;
     private String pageSize;
     private String gardenName;
     private String regionName;
 }

