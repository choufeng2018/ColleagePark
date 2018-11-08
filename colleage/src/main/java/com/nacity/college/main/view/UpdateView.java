package com.nacity.college.main.view;

import com.college.common_libs.domain.user.UpdateTo;
import com.nacity.college.base.BaseView;

/**
 * Created by xzz on 2017/9/13.
 **/

public interface UpdateView extends BaseView {
    void showUpdateDialog(UpdateTo updateTo);

    void installApk();
}
