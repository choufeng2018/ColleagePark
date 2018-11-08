package com.nacity.college.circle.view;


import com.college.common_libs.domain.circle.NeighborCommentTo;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.nacity.college.base.BaseView;

/**
 * Created by xzz on 2017/7/2.
 **/

public interface PostDetailView  extends BaseView {

     void  showDataSuccess(String message);

    void showMessage(String message);

    void getDataSuccess(NeighborPostTo postTo);

    void deletePostSuccess();

    void commentSuccess(NeighborCommentTo commentTo);

    void deleteCommentSuccess();

    void addPriseSuccess();
}
