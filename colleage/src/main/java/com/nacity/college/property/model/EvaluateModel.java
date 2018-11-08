package com.nacity.college.property.model;

/**
 * Created by xzz on 2017/10/12.
 **/

public interface EvaluateModel {

    void submitEvaluate(String serviceId, int attitude, int speed, int satisfaction, String content);
}
