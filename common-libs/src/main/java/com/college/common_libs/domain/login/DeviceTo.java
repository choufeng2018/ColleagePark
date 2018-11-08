package com.college.common_libs.domain.login;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Created by usb on 2017/11/22.
 */
@NoArgsConstructor
@Data
public class DeviceTo {
    private String deviceSid;
    /*    */   private String deviceType;
    /*    */   private String deviceToken;
    /*    */   private int deviceApp;
    /*    */   private int appVersion;
    /*    */   private double deviceLng;
    /*    */   private double deviceLat;
    /*    */   private String ownerSid;
    /*    */   private String apartmentSid;
}
