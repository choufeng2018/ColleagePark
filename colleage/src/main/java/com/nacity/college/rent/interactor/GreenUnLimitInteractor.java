package com.nacity.college.rent.interactor;


import com.college.common_libs.domain.rent.HouseListParam;
import com.college.common_libs.domain.rent.HouseRentTo;
import com.nacity.college.circle.LoadingCallback;

import java.util.List;


/**
 * Created by xzz on 2017/6/23.
 **/

public interface GreenUnLimitInteractor {
    /**
     *
     * 获取销售的房源
     */

    void getSaleHouseList(HouseListParam param, LoadingCallback<List<HouseRentTo>> loadingCallback);

}
