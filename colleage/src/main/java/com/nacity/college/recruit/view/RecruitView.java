package com.nacity.college.recruit.view;

import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.recruit.RecruitItemTo;
import com.nacity.college.base.BaseView;

import java.util.List;

/**
 *  Created by usb on 2017/9/20.
 */

public interface RecruitView extends BaseView {
    void getNewsList(List<RecruitItemTo> mList);
    void getAdSuccess(List<AdvertiseTo> advertiseList);

}
