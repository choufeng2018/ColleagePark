package com.college.common_libs.api;

import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.FeedbackParam;
import com.college.common_libs.domain.property.FeedbackTo;
import com.college.common_libs.domain.user.MainMenuParam;
import com.college.common_libs.domain.user.MainMenuTo;
import com.college.common_libs.domain.user.UpdateUserParam;
import com.college.common_libs.domain.user.UserIdParam;
import com.college.common_libs.domain.user.UserInfoTo;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xzz on 2017/9/13.
 **/

public interface UserApi {

    /***
     * 添加用户反馈信息
     *
     */
    @POST("api/feedback/saveOneFeedback")
    Observable<MessageTo<FeedbackTo>> addFeedbackInfo(@Body FeedbackParam param);

    /***
     *更新用户信息
     *
     */
    @POST("api/gardenUser/updateUserInfo")
    Observable<MessageTo<UserInfoTo>> updateUserInfo(@Body UpdateUserParam param);

    /***
     *获取用户首页按钮
     *
     */
    @POST("api/menu/selectAppMenuList")
    Observable<MessageTo<List<MainMenuTo>>> getMainMenu(@Body MainMenuParam param);


    /***
     *获取用户信息
     *
     */
    @POST("api/gardenUser/getUserInfoById")
    Observable<MessageTo<UserInfoTo>> getUserInfo(@Body UserIdParam param);

}
