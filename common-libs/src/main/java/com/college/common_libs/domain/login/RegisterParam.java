package com.college.common_libs.domain.login;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/9/17.
 **/

@NoArgsConstructor
@Data
public class RegisterParam {


    /**
     * gardenId :
     * userMobile :
     * validCode :
     * deviceType :
     * deviceApp :
     * appVersion :
     * deviceLng :
     * deviceLat :
     * registrationId :
     */

    private String gardenId;
    private String userMobile;
    private String validCode;
    private String deviceType="android";
    private String deviceApp="10";
    private String registrationId;
}
