package com.college.common_libs.domain.circle;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/9/27.
 **/

@NoArgsConstructor
@Data
public class CircleJoinTo {

    private String id;
    private String createUserId;
    private String createTime;
    private String content;
    private String nickname;
    private String userPic;
    private String postNickname;
    private String postUserPic;
    private String postId;
    private int postUserType;
    private String desc;
    private String userType;
}
