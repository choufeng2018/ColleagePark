package com.nacity.college.circle.interactor;


import com.college.common_libs.domain.circle.CircleJoinTo;
import com.nacity.college.circle.LoadingCallback;

import java.util.List;


/**
 * Created by xzz on 2017/7/2.
 **/

public interface JoinInteractor {
    /**
     * 获取参与列表数据
     */
    void getJoinList(String ownerSid, int pageIndex, LoadingCallback<List<CircleJoinTo>> callback);
}
