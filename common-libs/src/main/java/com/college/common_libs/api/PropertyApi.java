package com.college.common_libs.api;

import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.CancelReportParam;
import com.college.common_libs.domain.property.DecorateApplyListParam;
import com.college.common_libs.domain.property.DecorateApplyTo;
import com.college.common_libs.domain.property.DecorateDetailParam;
import com.college.common_libs.domain.property.DecorateParam;
import com.college.common_libs.domain.property.EvaluateServiceParam;
import com.college.common_libs.domain.property.FeedbackListParam;
import com.college.common_libs.domain.property.FeedbackParam;
import com.college.common_libs.domain.property.FeedbackTo;
import com.college.common_libs.domain.property.ParkingApplyListParam;
import com.college.common_libs.domain.property.ParkingApplyListTo;
import com.college.common_libs.domain.property.ParkingApplyTo;
import com.college.common_libs.domain.property.ParkingDetailParam;
import com.college.common_libs.domain.property.ParkingParam;
import com.college.common_libs.domain.property.PraiseDetailParam;
import com.college.common_libs.domain.property.RoomNumTo;
import com.college.common_libs.domain.property.RoomParam;
import com.college.common_libs.domain.property.ServiceIdParam;
import com.college.common_libs.domain.property.ServiceMainTo;
import com.college.common_libs.domain.property.ServiceRecordParam;
import com.college.common_libs.domain.property.ServiceRequestParam;
import com.college.common_libs.domain.property.ServiceTimeParam;
import com.college.common_libs.domain.property.ServiceTimeTo;
import com.college.common_libs.domain.property.ServiceTypeParam;
import com.college.common_libs.domain.property.ServiceTypeTo;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xzz on 2017/9/5.
 **/

public interface PropertyApi {
    /**
     * 获取各种服务种类
     **/
    @POST("api/service/getServiceTypeTree")
    Observable<MessageTo<List<ServiceTypeTo>>> findTypeByCategory(@Body ServiceTypeParam param);


    /***
     * 获取服务时间
     *
     */
    @POST("api/service/getRepairTimeByGardenId")
    Observable<MessageTo<List<ServiceTimeTo>>> findTime(@Body ServiceTimeParam param);

    /**
     *
     * 获取上传图片的token
     */
    @GET("api/vendor/qn-token")
    Observable<MessageTo<String>> getQnToken();

    /**
     * 添加服务
     *
     */
    @POST("api/service/submitRepairService")
    Observable<MessageTo<Boolean>> addServiceRequest(@Body ServiceRequestParam param);

    /**
     *
    *服务记录
     */
    @POST("api/service/getRepairServiceList")
    Observable<MessageTo<List<ServiceMainTo>>> findServiceInfoByApartmentAndOwner(@Body ServiceRecordParam param);
    /**
     *
    *装修申请记录
     */
    @POST("api/appDecoration/findDecorateApplyList")
    Observable<MessageTo<List<DecorateApplyTo>>> findDecorateApplyList(@Body DecorateApplyListParam param);
    /**
     *
    *装修申请
     */
    @POST("api/appDecoration/saveDecorateApply")
    Observable<MessageTo> saveDecorateApply(@Body DecorateParam param);

  /**
     *
    *装修申请详情
     */
    @POST("api/appDecoration/findDecorateApplyById")
    Observable<MessageTo<DecorateApplyTo>> findDecorateApplyById(@Body DecorateDetailParam param);

    /**
     *
    *获取房号
     */
    @POST("api/appDecoration/getRoomNum")
    Observable<MessageTo<List<RoomNumTo>>> getRoomNum(@Body RoomParam param);
    /**
     *
     *车位申请
     */
    @POST("api/parkApply/applyPark")
    Observable<MessageTo<ParkingApplyTo>> applyPark(@Body ParkingParam param);


 /**
     *
     *车位申请
     */
    @POST("api/parkApply/getMyApplyParkList")
    Observable<MessageTo<List<ParkingApplyListTo>>> findParkingApplyList(@Body ParkingApplyListParam param);

    /**
     *
     *车位申请详情
     */
    @POST("api/parkApply/getApplyParkById")
    Observable<MessageTo<ParkingApplyTo>> getApplyParkById(@Body ParkingDetailParam param);

    /***
     * 添加用户反馈信息
     *
     */
    @POST("api/feedback/saveOneFeedback")
    Observable<MessageTo<FeedbackTo>> addFeedbackInfo(@Body FeedbackParam param);

 /***
     * 获取最新服务记录
     *
     */
    @POST("api/service/getServiceCategory")
    Observable<MessageTo<String>> getServiceCategory(@Body ServiceTypeParam param);

    /****
     * 用户表扬建议列表
     *
     */
    @POST("api/feedback/getFeedbackList")
    Observable<MessageTo<List<FeedbackTo>>> findListByUser(@Body FeedbackListParam param);

    /****
     *表扬建议详情
     *
     */
    @POST("api/feedback/getOneFeedback")
    Observable<MessageTo<FeedbackTo>> getPraiseDetail(@Body PraiseDetailParam param);

    /****
     *获取服务详情
     *
     */
    @POST("api/service/getRepairServiceById")
    Observable<MessageTo<ServiceMainTo>> getServiceDetail(@Body ServiceIdParam param);

    /****
     *撤销预约
     *
     */
    @POST("api/service/cancelRepairServiceByServiceId")
    Observable<MessageTo<Boolean>> cancelReport(@Body CancelReportParam param);

    /****
     *评价服务
     *
     */
    @POST("api/service/evaluateService")
    Observable<MessageTo<Boolean>> evaluateService(@Body EvaluateServiceParam param);



}
