package com.college.common_libs.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/9/20.
 **/

@NoArgsConstructor
@Data
public class UpdateUserParam {
    private String userId;
    private String userPic;
    private String nickname;
    private String sex;
    private String updateGardenId;
    private String realName;
    private String userBirth;
}
