package com.college.common_libs.domain.user;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/9/27.
 **/

@NoArgsConstructor
@Data
public class MainMenuTo implements Serializable {


    private String id;
    private String code;
    private String showName;
    private String picUrl1;
    private String picUrl2;
    private String picUrl3;
    private String picUrl4;
    private String articleId;
    private String title;
    private String summary;
    private String phone;
    private String lastModTime;
    private String browseTimes;
    private String articleType;
    private String picUrl;
}
