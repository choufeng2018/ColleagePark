package com.nacity.college.login.model;

import com.college.common_libs.domain.AdInfoTo;

import java.util.List;

/**
 * Created by xzz on 2017/8/25.
 **/

public interface SplashModel {


  List<AdInfoTo>getAdList(String apartmentSid, int type);
}
