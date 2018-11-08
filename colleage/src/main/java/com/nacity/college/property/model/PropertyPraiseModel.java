package com.nacity.college.property.model;


import java.util.ArrayList;

/**
 * Created by xzz on 2017/9/6.
 **/

public interface PropertyPraiseModel  {

    void uploadImage(ArrayList<String> imagePathList, int photoNumber);

    void submit(String imagePath, int praiseType, String contact, String praiseContent);
}
