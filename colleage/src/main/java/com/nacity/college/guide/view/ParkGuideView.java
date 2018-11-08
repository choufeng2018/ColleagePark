package com.nacity.college.guide.view;

import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.apartment.GuideHouseTypeTo;
import com.college.common_libs.domain.guide.IntroduceTo;
import com.nacity.college.base.BaseView;

import java.util.List;

/**
 * Created by xzz on 2017/9/22.
 **/

public interface ParkGuideView<T> extends BaseView<T> {

    void getIntroduceSuccess(List<IntroduceTo> introduceList);

    void refreshRecycleView(List<T> list);

    void setHouse(List<GuideHouseTypeTo> houseList);

    void getAdSuccess(List<AdvertiseTo> advertiseList);
}
