package com.nacity.college.common.model;

/**
 * Created by xzz on 2017/9/20.
 **/

public interface UpdateUserInfoModel {

    void uploadHeadImage(String imagePath, int photoNumber);

    void updateUserInfo(String userPic, String nickName, String realName, String birthday, String sex, String newParkId);

}
