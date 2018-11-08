package com.college.common_libs.domain.circle;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by xz on 2017/7/2.
 **/
@Data
public class OwnerMessageTo implements Serializable {
    private static final long serialVersionUID = 1L;



    //昵称
    private String familyName;
    //头像
    private String ownerImage;

    //用户sid
    private String ownerSid;

    private int flag;
    private String createdOn;


}
