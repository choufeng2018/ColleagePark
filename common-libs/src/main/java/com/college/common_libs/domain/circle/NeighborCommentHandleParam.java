 package com.college.common_libs.domain.circle;

 import java.io.Serializable;

 import lombok.Data;

 @Data
 public class NeighborCommentHandleParam implements Serializable {
   private static final long serialVersionUID = 1L;
   private String postSid;
   private String commentSid;
   private String userSid;
}

