package com.nacity.college.circle.interactor;


import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.nacity.college.circle.LoadingCallback;

import java.util.List;


/**
 * Created by xzz on 2017/7/1.
 **/

public interface LifePersonalInteractor {

    void getPersonalPostList(String apartmentSid, String otherSid, String userSid, int pageIndex, LoadingCallback<MessageTo<List<NeighborPostTo>>> callback);
}
