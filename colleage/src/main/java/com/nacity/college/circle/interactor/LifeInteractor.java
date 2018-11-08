package com.nacity.college.circle.interactor;


import com.college.common_libs.domain.circle.NeighborPostTo;
import com.college.common_libs.domain.circle.NeighborPostTypeTo;
import com.nacity.college.circle.BaseCallback;
import com.nacity.college.circle.LoadingCallback;

import java.util.List;


/**
 * Created by xzz on 2017/6/29.
 **/

public interface LifeInteractor {
    /**
     * 获取生活种类
     */
    void getLifeCategory(CategoryCallback callback);

    interface CategoryCallback extends BaseCallback {

        void getCateGorySuccess(List<NeighborPostTypeTo> postTypeList);
    }

    void getLifePostList(String userSid, String typeId, int pageIndex, LoadingCallback<List<NeighborPostTo>> callback);
}
