 package com.college.common_libs.domain.circle;

 import java.io.Serializable;

 import lombok.Data;

 @Data
 public class NeighborHandleParam implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private String postId;
   private String createUserId;
 }
