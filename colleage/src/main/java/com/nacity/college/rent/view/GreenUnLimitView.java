package com.nacity.college.rent.view;

import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.nacity.college.base.BaseRecycleView;

import java.util.List;


/**
 * Created by xzz on 2017/6/23.
 */

public interface GreenUnLimitView<T> extends BaseRecycleView<T> {

    void getAdSuccess(List<AdvertiseTo> advertiseTos);
}
