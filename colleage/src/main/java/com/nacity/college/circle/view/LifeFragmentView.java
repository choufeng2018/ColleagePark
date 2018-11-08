package com.nacity.college.circle.view;

import com.college.common_libs.domain.circle.NeighborLikeTo;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.nacity.college.base.BaseView;

import java.util.List;


/**
 * Created by xzz on 2017/6/30.
 **/

public interface LifeFragmentView extends BaseView {

    void refreshRecycleView(List<NeighborPostTo> postList);

    List<NeighborLikeTo> getLikeList();


    void addPraiseSuccess(List<NeighborLikeTo> likeList);

    void deletePostSuccess();

}
