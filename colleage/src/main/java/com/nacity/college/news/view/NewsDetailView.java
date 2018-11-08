package com.nacity.college.news.view;

import com.college.common_libs.domain.news.NewsTo;
import com.nacity.college.base.BaseView;

/**
 *  Created by usb on 2017/9/18.
 */

public interface NewsDetailView extends BaseView {
    void getNewsDetail(NewsTo mNews);
}
