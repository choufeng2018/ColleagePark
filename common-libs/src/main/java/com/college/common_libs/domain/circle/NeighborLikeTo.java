package com.college.common_libs.domain.circle;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NeighborLikeTo implements Serializable
 {


     /**
      * id : 360452940734922752
      * createUserId : 1
      * createTime : 2017-09-21 17:00:30
      * postId : 360430729777119232
      * userName : 超级管理员
      * nickname : 小悦悦
      * userPic : null
      * userType : 1
      * userMobile : 1
      * userTel : 1
      * roomNo : null
      * gardenName : null
      * createTimeDesc : 3天前
      */

     private String id;
     private String createUserId;
     private String createTime;
     private String postId;
     private String userName;
     private String nickname;
     private String userPic;
     private int userType;
     private String userMobile;
     private String userTel;
     private String roomNo;
     private String gardenName;
     private String createTimeDesc;
 }

