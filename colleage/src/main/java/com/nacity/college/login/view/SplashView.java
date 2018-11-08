package com.nacity.college.login.view;

import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.nacity.college.base.BaseView;

/**
 * Created by xzz on 2017/8/25.
 **/

public interface SplashView extends BaseView {

   void setLoadingAd(AdvertiseTo adInfoTo, long delayTime);
}
