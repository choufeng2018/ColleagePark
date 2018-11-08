package com.nacity.college.guide.model;

/**
 * Created by xzz on 2017/9/22.
 **/

public interface ParkGuideModel {

    void getParkIntroduceList();

    void getParkHouse();

    void getEnterPriseLit(int pageIndex, String house, int type);
}
