package com.college.common_libs.api;

import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.apartment.GuideHouseTypeTo;
import com.college.common_libs.domain.apartment.ParkIdParam;
import com.college.common_libs.domain.guide.EnterpriseListParam;
import com.college.common_libs.domain.guide.EnterpriseTo;
import com.college.common_libs.domain.guide.IntroduceListParam;
import com.college.common_libs.domain.guide.IntroduceTo;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xzz on 2017/9/22.
 **/

public interface GuideApi {

    /**
    *获取园区介绍列表
     */
    @POST("api/introduce/selectModelVoListPage")
    Observable<MessageTo<List<IntroduceTo>>>getIntroduceList(@Body IntroduceListParam param);

    /**
     *获取园区企业所有楼宇
     */
    @POST("api/license/getBuildingListByGardenId")
    Observable<MessageTo<List<GuideHouseTypeTo>>>getEnterpriseHouse(@Body ParkIdParam param);

    /**
     *获取园区企业通过楼宇搜索
     */
    @POST("api/license/getLicenseList")
    Observable<MessageTo<List<EnterpriseTo>>>getEnterpriseList(@Body EnterpriseListParam param);

}
