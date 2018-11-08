package com.nacity.college.common.model;

import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.nacity.college.base.impl.SuccessCallback;

import java.util.List;

/**
 * Created by xzz on 2017/9/19.
 **/

public interface ApartmentAdModel {

   void getApartmentAd(int type, String parkName, SuccessCallback<List<AdvertiseTo>> callback);
}
