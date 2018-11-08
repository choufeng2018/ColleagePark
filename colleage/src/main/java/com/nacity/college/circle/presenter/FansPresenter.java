package com.nacity.college.circle.presenter;


import com.college.common_libs.domain.circle.CareListTo;

/**
 * Created by xzz on 2017/7/2.
 **/

public interface FansPresenter {
    /**
     * 获取关注的list
     */
     void getFansList(int pageIndex);

    /**
     * 关注别人
     */
    void careOther(CareListTo ownerTo);
}
