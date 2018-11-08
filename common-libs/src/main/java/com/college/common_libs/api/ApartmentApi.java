package com.college.common_libs.api;

import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.apartment.AdvertiseParam;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.apartment.ApartmentInfoTo;
import com.college.common_libs.domain.apartment.ApartmentParam;
import com.college.common_libs.domain.apartment.ParkCityParam;
import com.college.common_libs.domain.apartment.ParkCityTo;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xzz on 2017/9/17.
 **/

public interface ApartmentApi {

    /**
     * 获取所有城市
     */
    @POST("api/garden/getCityList")
    Observable<MessageTo<List<ParkCityTo>>>getCityByName(@Body ParkCityParam param);

    /**
     * 获取城市小区和搜索当前城市小区
     */
    @POST("api/garden/getGardenInfoByCity")
    Observable<MessageTo<List<ApartmentInfoTo>>>getApartmentByNameAndCityId(@Body ApartmentParam param);

    /**
     * 获取小区广告
     */

    @POST("api/adGarden/findAdGardenList")
    Observable<MessageTo<List<AdvertiseTo>>>getParkAd(@Body AdvertiseParam param);
}
