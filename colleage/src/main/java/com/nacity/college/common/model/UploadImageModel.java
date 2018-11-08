package com.nacity.college.common.model;

import com.nacity.college.base.impl.LoadCallback;

import java.util.ArrayList;

/**
 * Created by xzz on 2017/9/5.
 **/

public interface UploadImageModel {

    void uploadImage(ArrayList<String> imagePathList, int photoNumber, LoadCallback<String> callback);


}
