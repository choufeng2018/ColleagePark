package com.college.common_libs.domain.login;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Created by usb on 2017/11/22.
 */
@NoArgsConstructor
@Data
public class DeviceParam {
 /**   registrationId true string 极光ID
    userId true string 用户ID
    deviceType true string 设备类型（android ios)
    deviceApp false string APP标识（android 10=业主；ios 20=业主）
    appVersion false string APP版本
    deviceLng false string 设备维度
    deviceLat false string 设备经度
    private String deviceType;**/

     /*    */   private String registrationId;
    /*    */   private String userId;
    /*    */   private String deviceType;
    /*    */   private String deviceApp;
    /*    */   private String appVersion;
    /*    */   private String deviceLng;
    /*    */   private String deviceLat;

}
