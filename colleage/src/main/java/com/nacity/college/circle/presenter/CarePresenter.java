package com.nacity.college.circle.presenter;


import com.college.common_libs.domain.circle.CareListTo;

/**
 * Created by xzz on 2017/7/2.
 **/

public interface CarePresenter {
    /**
     * 获取关注的list
     */
    public void getCareList(int pageIndex);


    /**
     * 取消关注
     */
    void cancelCare(CareListTo ownerTo);
}
