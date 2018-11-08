package com.nacity.college.recruit.model;

/**
 * Created by usb on 2017/9/20.
 */

public interface RecruitModel {
    void getRecruitList(int index, String limit);
    void getRecruitListByLicenseId(String licenseId, int index);
}
