package com.nacity.college.main.model;

import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by xzz on 2017/9/13.
 **/

public interface UpdateModel {

    void getUpdateInfo();

    void downloadApp(String url, ProgressBar progressBar, TextView updateProgress);

}
