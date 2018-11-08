package com.nacity.college.circle.presenter;

import android.widget.EditText;

import java.util.ArrayList;


/**
 * Created by xzz on 2017/7/1.
 **/

public interface PostPresenter {

    void addPost(EditText postContent, String typeSi);


    void UploadImage(String path, ArrayList<String> pathList);
}
