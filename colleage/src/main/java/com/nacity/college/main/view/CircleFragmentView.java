package com.nacity.college.main.view;

import com.college.common_libs.domain.circle.NeighborPostTypeTo;
import com.nacity.college.base.BaseView;

import java.util.List;

/**
 * Created by xzz on 2017/9/24.
 **/

public interface CircleFragmentView extends BaseView {

    void getTypeSuccess(List<NeighborPostTypeTo> data);
}
