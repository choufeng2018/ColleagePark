package com.nacity.college.circle.presenter;

/**
 * Created by xzz on 2017/6/30.
 **/

public interface LifeFragmentPresenter {
    /**
     * 获取生活帖子列表
     */
    void getLifePostData(String typeSid, int pageIndex);

    /**
     * 添加点赞
     */
    void addPraise(String postSid);

    /**
     *
     * 删除自己发的帖子
     */
    void deletePost(String postSid);

    void deletePraise(String postSid);
}
