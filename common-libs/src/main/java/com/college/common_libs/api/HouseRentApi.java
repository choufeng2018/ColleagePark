package com.college.common_libs.api;

import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.rent.HouseListParam;
import com.college.common_libs.domain.rent.HouseRentTo;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xzz on 2017/6/23.
 **/

public interface HouseRentApi {

    /***
     * 返回当前城市所有出租或出售信息
     *
     * @param param 查询条件
     */

    @POST("api/RenalHouse/findRenalHouseList")
    Observable<MessageTo<List<HouseRentTo>>> findAllHouseInfo(@Body HouseListParam param);

}
