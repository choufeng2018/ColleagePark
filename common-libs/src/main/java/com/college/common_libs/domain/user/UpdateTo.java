package com.college.common_libs.domain.user;

import lombok.Data;

/**
 * Created by xzz on 2017/7/13.
 **/
@Data
public class UpdateTo {

    /**
     * appName : iHome
     * forceUpdate : 0
     * appSize : 22.4MB
     * downloadUrl : http://139.129.166.169/app/joyhome-app-android.apk
     * verCode : 70
     * updateDesc : Ver2.4.9
     1、 评价服务工单时选择差评新增评价标签
     */

    private String appName;
    private int forceUpdate;
    private String appSize;
    private String downloadUrl;
    private int verCode;
    private String updateDesc;
    private String appVersion;


}
