package com.nacity.college.main.view;

import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.user.MainMenuTo;
import com.nacity.college.base.BaseView;

import java.util.List;

/**
 * Created by xzz on 2017/9/19.
 **/

public interface MainHomeView extends BaseView {

    void getAdSuccess(List<AdvertiseTo> advertiseList);

    void getParkServiceSuccess(List<MainMenuTo> data);

    void getPropertySuccess(List<MainMenuTo> data);

    void getServiceListSuccess(List<MainMenuTo> data);
}
