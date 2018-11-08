 package com.college.common_libs.domain.property;

 import java.io.Serializable;

 import lombok.Data;
 import lombok.NoArgsConstructor;

 @NoArgsConstructor
 @Data
 public class FeedbackListParam implements Serializable
 {


     private int pageIndex;
     private String feedbackUserId;
     private String gardenId;
     private int feedbackType;
 }
