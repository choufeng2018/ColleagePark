package com.nacity.college.circle.presenter;



import com.college.common_libs.domain.AdInfoTo;
import com.college.common_libs.domain.user.UserInfoTo;

import java.util.List;


/**
 * Created by xzz on 2017/6/26.
 */

public interface CommonInteractor {


    /**
     *获取轮播图广告
     */
   void getAd(String apartmentSid, String type, AdLoadingFinish loadingFinish);

    interface AdLoadingFinish{

        void adLoadSuccess(List<AdInfoTo> adInfoList);
    }



    /**
     *上传图片到七牛前获取token
     */

    void getToken(String path, TokenFinish tokenFinish);

    interface TokenFinish{
        void tokenSuccess(String token, String path);

        void error();
    }

    /**
     *上传图片到七牛
     */
    void uploadImage(String key, String userSid, UpImageFinish upImageFinish);

    interface UpImageFinish{
        void upImageSuccess(UserInfoTo userInfo);

        void message(String message);

        void error();
    }



}
