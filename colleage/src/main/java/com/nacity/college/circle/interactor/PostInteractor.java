package com.nacity.college.circle.interactor;


import com.college.common_libs.domain.circle.NeighborPostParam;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.nacity.college.circle.LoadingCallback;

/**
 * Created by xzz on 2017/7/1.
 **/

public interface PostInteractor {

    /**
     * 发贴
     */
    void lifePost(NeighborPostParam param, LoadingCallback<NeighborPostTo> callback);
}
