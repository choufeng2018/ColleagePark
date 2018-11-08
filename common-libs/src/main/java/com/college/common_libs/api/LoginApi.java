package com.college.common_libs.api;

import com.college.common_libs.domain.AdInfoTo;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.login.DeviceParam;
import com.college.common_libs.domain.login.LoginParam;
import com.college.common_libs.domain.login.RegisterParam;
import com.college.common_libs.domain.login.VerificationCodeParam;
import com.college.common_libs.domain.user.UserInfoTo;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by xzz on 2017/8/29.
 **/

public interface LoginApi {

    /**
     * 抓取当前小区发布的广告
     *
     */
    @GET("api/v1/common/ad/{apartmentSid}/{type}")
    Observable<MessageTo<List<AdInfoTo>>>findAdListInfoByApartmentSid(@Path("apartmentSid") String apartmentSid, @Path("type") int type);


    /**
     * 获取注册登录验证码
     */
    @POST("api/gardenUser/getIdentifyCode")
    Observable<MessageTo>getVerificationCode(@Body VerificationCodeParam param);



    /**
     * 园区注册
     */
    @POST("api/gardenUser/RegisterUser")
    Observable<MessageTo<UserInfoTo>>register(@Body RegisterParam param);


    /**
     * 园区登录
     */
    @POST("api/gardenUser/userLogin")
    Observable<MessageTo<UserInfoTo>>login(@Body LoginParam param);

    /**
     * 更新业主当前设备id
     *
     */
    @POST("api/gardenUser/updateDeviceInfo")
    Observable<MessageTo> updateOwnerDeviceInfo(@Body DeviceParam param);

    /**
     * 退出App清除设备id
     *
     */
    @POST("api/gardenUser/cancelUser")
    Observable<MessageTo> cancelUser(@Body DeviceParam param);
}
