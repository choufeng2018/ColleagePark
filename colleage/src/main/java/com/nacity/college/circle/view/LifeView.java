package com.nacity.college.circle.view;


import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.college.common_libs.domain.circle.NeighborCommentTo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xzz on 2017/6/29.
 **/

public interface LifeView {

    void setTpi(String[] titles);


    void initFragment(List<Fragment> fragmentList);

    void setTypeList(ArrayList<String> typeList);

    TextView getSubTextView();


    void getNewMessageSuccess(List<NeighborCommentTo> data);

    void loadingDismiss();
}
