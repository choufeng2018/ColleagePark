package com.nacity.college.circle.presenter;


import com.college.common_libs.domain.circle.OwnerMessageTo;

/**
 * Created by xzz on 2017/7/1.
 **/

public interface LifePersonalPresenter {

    /**
     *获取个人或者别人主页帖子
     */
    void getPostList(String otherSid, int pageIndex);

    /**
     *关注某人
     */
    void careOther(String userSid, String otherSid);

    /**
     *取消关注
     */
    void cancelCare(OwnerMessageTo ownerTo);


    /**
     * 添加点赞
     */
    void addPraise(String postSid);

    /**
     * 删除帖子
     */
    void deletePost(String postSid);
}
