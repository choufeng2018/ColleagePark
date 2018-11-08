package com.nacity.college.circle.view;

import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.circle.NeighborLikeTo;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.nacity.college.base.BaseView;

import java.util.List;


/**
 * Created by xzz on 2017/7/1.
 **/

public interface LifePersonView  extends BaseView {

    void refreshRecycleView(MessageTo<List<NeighborPostTo>> postList);

    void careOtherSuccess();

    void cancelOtherSuccess();


    List<NeighborLikeTo> getLikeList();


    void addPraiseSuccess(List<NeighborLikeTo> likeList);

    void deletePostSuccess();
}
