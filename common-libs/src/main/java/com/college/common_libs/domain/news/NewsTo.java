package com.college.common_libs.domain.news;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by usb on 2017/9/19.
 */

@Data
public class NewsTo   implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id : 356863341697695744
     * createUserId : 1
     * createTime : 2017-09-11 18:07:31
     * modUserId : 1
     * lastModTime : 2017-09-12 10:08:42
     * gardenId : 1
     * type : 3
     * noticeType : null
     * picUrl : null
     * title : 这是一条法律服务
     * browseTimes : 10
     * isTop : 0
     * topTime : 2017-09-18 18:12:18
     * isdel : N
     * content : <p>法律服务啊</p>
     */

    private String id;
    private String createUserId;
    private String userName;
    private String createTime;
    private String modUserId;
    private String lastModTime;
    private String endTime;
    private String gardenId;
    private int    type;
    private String noticeType;
    private String picUrl;
    private String title;
    private String    browseTimes;
    private int    isTop;
    private String topTime;
    private String isdel;
    private String content;
    private String phone;
    private int status;
    private int showButton;
    private int isSignUp;
    private String summary;
    private String isFull;


}