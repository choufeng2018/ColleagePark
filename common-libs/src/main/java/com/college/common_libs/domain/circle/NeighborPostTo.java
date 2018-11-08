package com.college.common_libs.domain.circle;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NeighborPostTo implements Serializable {

    /**
     * id : 356834678944038914
     * createUserId : 117
     * createTime : 2017-09-19 01:25:57
     * modUserId : 1
     * lastModTime : 2017-09-20 17:00:25
     * gardenId : 355396305909972993
     * typeId : 2
     * urlTitle : 铁
     * postUrl : null
     * status : 1
     * images : null
     * isTop : 1
     * topTime : 2017-09-20 17:00:25
     * isdel : N
     * content : 这是一个帖子
     * roomNo : null
     * gardenName : 联合大厦
     * userName : 段江涛
     * nickname : 小悦悦
     * userPic : null
     * userType : 2
     * postTypeDesc : 紧急通知
     * userMobile : 18535730148
     * userTel :
     * typeIndex : 2
     * statusDesc : 审核通过
     * isTopDesc : 是
     * nicknames : null
     * imageList : null
     * createTimeDesc : 5天前
     * postCommentVoList : null
     * postPraiseVoList : null
     * commentTotal : 0
     * praiseTotal : 0
     */

    private String id;
    private String createUserId;
    private String createTime;
    private String modUserId;
    private String lastModTime;
    private String gardenId;
    private String typeId;
    private String urlTitle;
    private String postUrl;
    private int status;
    private String images;
    private int isTop;
    private String topTime;
    private String isdel;
    private String content;
    private String roomNo;
    private String gardenName;
    private String userName;
    private String nickname;
    private String userPic;
    private int userType;
    private String postTypeDesc;
    private String userMobile;
    private String userTel;
    private int typeIndex;
    private String statusDesc;
    private String isTopDesc;
    private String nicknames;
    private List<String> imageList;
    private String createTimeDesc;
    private String commentTotal;
    private String praiseTotal;
    private String subPostContent="";
    private boolean isPraised;
    private List<NeighborCommentTo> postCommentVoList;
    private List<NeighborLikeTo>postPraiseVoList;
    private String replyTargetNickname;
    private boolean isFollowed;
    private String desc;

}
