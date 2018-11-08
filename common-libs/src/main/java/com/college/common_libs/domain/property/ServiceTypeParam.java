 package com.college.common_libs.domain.property;

 import java.io.Serializable;

 import lombok.Data;

 @Data
 public class ServiceTypeParam
   implements Serializable
 {
   private String categoryType;
   private String loginUserId;
   private String gardenId;

}
