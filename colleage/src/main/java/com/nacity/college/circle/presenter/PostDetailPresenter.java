package com.nacity.college.circle.presenter;


import com.college.common_libs.domain.circle.NeighborCommentTo;
import com.college.common_libs.domain.circle.NeighborPostTo;

/**
 * Created by xzz on 2017/7/2.
 **/

public interface PostDetailPresenter {
    /**
     *
     *获取帖子详情
     */
    void getPostDetail(String postSid);

    /**
     *
     * 删除自己发的帖子
     */
    void deletePost(String postSid);

    /**
     *
     *添加评论
     */
    void addComment(NeighborPostTo mode, NeighborCommentTo commentTo, String commentContent);

    /**
     * 删除评论
     */
    void deleteComment(String commentSid);

    void praise(String postSid);
}
