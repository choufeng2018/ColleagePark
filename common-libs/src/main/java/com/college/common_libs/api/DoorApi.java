package com.college.common_libs.api;


import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.door.ApplyDoorParam;
import com.college.common_libs.domain.door.ApplyDoorRecordTo;
import com.college.common_libs.domain.door.CanApplyDoorTo;
import com.college.common_libs.domain.door.DoorApplyRecordParam;
import com.college.common_libs.domain.door.DoorListParam;
import com.college.common_libs.domain.door.OpenDoorLogParam;
import com.college.common_libs.domain.door.OpenDoorTo;
import com.college.common_libs.domain.door.OpenDoorTypeTo;
import com.college.common_libs.domain.door.RemoteDoorMessageTo;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Created by xzz on 2017/11/3.
 **/
public interface DoorApi {


    /**
     * 获取小区门开放状态
     */
    @POST("api/userDoor/getApartmentDeviceMinVo")
    Observable<MessageTo<OpenDoorTypeTo>> openDoorType(@Body DoorListParam param);

    /**
     * 获取小区开门列表
     */
    @POST("api/userDoor/getOpenDoorMainVoByParam")
    Observable<MessageTo<OpenDoorTo>> getDoorList(@Body DoorListParam param);

    /**
     * 获取远程开门列表
     */
    @POST("api/userDoor/getOpenDoorMainVoMinByParam")
    Observable<MessageTo<OpenDoorTo>> getRemoteDoorList(@Body DoorListParam param);



    /**
     * 获取申请门禁记录列表
     */
    @POST("api/userDoor/getDoorApplyVoList")
    Observable<MessageTo<List<ApplyDoorRecordTo>>> getApplyRecord(@Body DoorApplyRecordParam param);

    /**
     * 可申请门禁列表
     */
    @POST("api/userDoor/getSelectApartmentDoorVoListByParam")
    Observable<MessageTo<List<CanApplyDoorTo>>> getApplyDoorList(@Body DoorListParam param);

    /**
     * 申请门
     */
    @POST("api/userDoor/saveApiUserDoor")
    Observable<MessageTo> applyOpenDoor(@Body ApplyDoorParam param);


    /**
     * 开门日志
     */
    @POST("api/userDoor/saveOpenDoorLog")
    Observable<MessageTo<Boolean>> sendOpenDoorLog(@Body OpenDoorLogParam param);



    /**
     * 远程开门
     */
    @FormUrlEncoded
    @POST("key/remoteOpenDoor/8C0868F49FA63C686CB8AB83691515AA")
    Observable<RemoteDoorMessageTo> remoteOpenDoor(@Field("MESSAGE") String head);

//    /**
//     * 访客二维码
//     */
//
//    @POST("/api/userDoor/makeOwnerVisitorQrCode")
//    void visitorCode(@Body VisitorDoorParam param, Callback<MessageTo<VisitorDoorTo>> callback);

//    /**
//     * 判断是否有令令
//     */
//
//    @POST("/api/userDoor/getOwnerVisitorQrCode")
//    void chaeckHaveCode(@Body VisitorDoorParam param, Callback<MessageTo<VisitorDoorTo>> callback);


    /**
     * 小区是否显示远程开门
     */
    @POST("api/userDoor/getApartmentDeviceMinVoOther")
    Observable<MessageTo<OpenDoorTypeTo>> displayRemoteDoor(@Body DoorListParam param);

}
