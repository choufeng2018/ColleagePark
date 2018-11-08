 package com.college.common_libs.domain.circle;

 import java.io.Serializable;

 import lombok.Data;
 import lombok.NoArgsConstructor;

 @NoArgsConstructor
 @Data
 public class NeighborCommentParam implements Serializable {


     private String createUserId;
     private String postId;
     private String content;
     private String replyUserId;
     private String replyCommentId;
 }

