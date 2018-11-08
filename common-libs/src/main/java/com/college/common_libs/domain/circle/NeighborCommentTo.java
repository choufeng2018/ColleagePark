package com.college.common_libs.domain.circle;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NeighborCommentTo implements Serializable {




    private String id;
    private String createUserId;
    private String createTime;
    private String modUserId;
    private String lastModTime;
    private String postId;
    private String replyCommentId;
    private String replyUserId;
    private String isdel;
    private String content;
    private String userName;
    private String nickname;
    private String userPic;
    private int userType;
    private String userMobile;
    private String userTel;
    private String replyTargetUserName;
    private String replyTargetNickname;
    private String replyTargetUserPic;
    private String replyTargetUserMobile;
    private String replyTargetUserTel;
    private String roomNo;
    private String gardenName;
    private String createTimeDesc;
    private String subComment;

}
