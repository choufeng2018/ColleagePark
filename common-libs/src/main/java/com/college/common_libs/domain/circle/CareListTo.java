package com.college.common_libs.domain.circle;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/9/27.
 **/

@NoArgsConstructor
@Data
public class CareListTo {

    /**
     * id : 88
     * userId : 16
     * createTime : mock
     * nickname : mock
     * userPic : mock
     * followNickname : mock
     * followUserPic : mock
     */

    private String id;
    private String userId;
    private String createTime;
    private String nickname;
    private String userPic;
    private String followNickname;
    private String followUserPic;
    private String followUserId;
    private int followUserType;
    private int  userType;
    private boolean isFollowed;
}
