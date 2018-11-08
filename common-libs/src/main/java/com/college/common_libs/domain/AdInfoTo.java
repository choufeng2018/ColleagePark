 package com.college.common_libs.domain;

 import java.io.Serializable;

 import lombok.Data;


 @Data
 public class AdInfoTo implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private String adSid;
   private String adTitle;
   private int adIndex;
   private String adImage;
   private String adUrl;
   private String adContent;
   private String adInnerUrl;
}

