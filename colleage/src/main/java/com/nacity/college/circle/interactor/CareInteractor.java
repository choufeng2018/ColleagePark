package com.nacity.college.circle.interactor;


import com.college.common_libs.domain.circle.CareListTo;
import com.nacity.college.circle.LoadingCallback;

import java.util.List;


/**
 * Created by xzz on 2017/7/2.
 **/

public interface CareInteractor {
    /**
     * 获取参与列表数据
     */
    void getCareList(String ownerSid, int pageIndex, LoadingCallback<List<CareListTo>> callback);
}
