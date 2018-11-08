package com.nacity.college.news.view;

import com.college.common_libs.domain.news.NewsTo;
import com.nacity.college.base.BaseView;

import java.util.List;

/**
 *  Created by usb on 2017/9/4.
 */

public interface NewsFragmentView extends BaseView {
    void getNewsList(List<NewsTo> mList);
}
