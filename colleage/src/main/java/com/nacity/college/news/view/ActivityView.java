package com.nacity.college.news.view;

import com.college.common_libs.domain.news.NewsTo;
import com.nacity.college.base.BaseView;

/**
 *  Created by usb on 2017/12/11.
 */

public interface ActivityView extends BaseView {
    void getActivityInfo(NewsTo newsTo);
}
