 package com.college.common_libs.domain.property;

 import java.io.Serializable;

 import lombok.Data;

 @Data

 public class FeedbackParam implements Serializable
 {



     private String feedbackUserId;
     private String gardenId;
     private int feedbackType;
     private String feedbackContent;
     private String feedbackImages;
     private String feedbackContact;
 }




