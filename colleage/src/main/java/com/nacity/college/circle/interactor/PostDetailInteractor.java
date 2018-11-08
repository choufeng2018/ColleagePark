package com.nacity.college.circle.interactor;


import com.college.common_libs.domain.circle.NeighborPostTo;
import com.nacity.college.circle.LoadingCallback;

/**
 * Created by xzz on 2017/7/2.
 **/

public interface PostDetailInteractor {


    void getPostDetail(String postSid, String userSid, LoadingCallback<NeighborPostTo> callback);
}
