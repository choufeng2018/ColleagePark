package com.college.common_libs.domain.property;


import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class FeedbackTo implements Serializable {


    private String feedbackId;
    private String gardenId;
    private String feedbackType;
    private String feedbackTypeDesc;
    private String feedbackImages;
    private String feedbackContent;
    private String replyContent;
    private Date createTime;
    private Date lastModTime;
    private String feedbackContact;

}
