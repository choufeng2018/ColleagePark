package com.college.common_libs.api;


import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.circle.CareListTo;
import com.college.common_libs.domain.circle.CircleCareListParam;
import com.college.common_libs.domain.circle.CircleJoinParam;
import com.college.common_libs.domain.circle.CircleJoinTo;
import com.college.common_libs.domain.circle.CirclePostListParam;
import com.college.common_libs.domain.circle.DeletePostParam;
import com.college.common_libs.domain.circle.DeletePraiseParam;
import com.college.common_libs.domain.circle.FansListParam;
import com.college.common_libs.domain.circle.NeighborCommentParam;
import com.college.common_libs.domain.circle.NeighborCommentTo;
import com.college.common_libs.domain.circle.NeighborHandleParam;
import com.college.common_libs.domain.circle.NeighborLikeTo;
import com.college.common_libs.domain.circle.NeighborMessageParam;
import com.college.common_libs.domain.circle.NeighborPostParam;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.college.common_libs.domain.circle.NeighborPostTypeTo;
import com.college.common_libs.domain.circle.NeighborhoodUserConnectParam;
import com.college.common_libs.domain.circle.NeighborhoodUserConnectTo;
import com.college.common_libs.domain.circle.PostDetailParam;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by xzz on 2017/6/29.
 **/

public interface CircleApi {

    /**
     *获取生活的种类
     */
    @POST("api/postType/getPostTypeList")
    Observable<MessageTo<List<NeighborPostTypeTo>>> getPostTypeList();




    /**
     * 根据小区ID 帖子种类ID获取帖子
     *
     */
    @POST("api/post/getPostList")
    Observable<MessageTo<List<NeighborPostTo>>> getPostList(@Body CirclePostListParam param);


    /**
     * 个人主页
     *
     */
    @POST("api/userFollow/getHomePage")
    Observable<MessageTo<List<NeighborPostTo>>> getAllPost(@Body CirclePostListParam param);

    /**
     * 添加生活发帖
     *
     */
    @POST("api/post/saveOnePost")
    Observable<MessageTo<NeighborPostTo>> addNeighborPost(@Body NeighborPostParam param);


    /**
     * 获取关注列表
     *
     */
    @POST("api/userFollow/getMyFollowListByPage")
    Observable<MessageTo<List<CareListTo>>> getCareList(@Body CircleCareListParam param);
    /**
     * 获取粉丝列表
     *
     */
    @POST("api/userFollow/getMyFansListByPage")
    Observable<MessageTo<List<CareListTo>>> getFansList(@Body FansListParam param);


    /**
     *获取我参与的帖子
     */
    @POST("api/userFollow/getMyJoinListByPage")
    Observable<MessageTo<List<CircleJoinTo>>> getMyNeighborJoin(@Body CircleJoinParam param);

    /**
     * 关注别人
     *
     */
    @POST("api/userFollow/saveOrDeleteOneUserFollow")
    Observable<MessageTo<NeighborhoodUserConnectTo>> careOther(@Body NeighborhoodUserConnectParam param);
    /**
     * 删除关注人
     *
     */
    @GET("api/v1/neighbor/deleteNeighborhoodUserConnectUser/{ownerSid}/{toOwnerSid}")
    Observable<MessageTo<Boolean>> deleteCare(@Path("ownerSid") String ownerSid, @Path("toOwnerSid") String toOwnerSid);

    /**
     * 添加邻居圈点赞
     *
     */
    @POST("api/postPraise/saveOrDeleteOnePostPraise")
    Observable<MessageTo<NeighborLikeTo>> addNeighborLike(@Body NeighborHandleParam param);

    /**
     * 删除邻居圈点赞
     *
     */
    @POST("api/postPraise/deleteOnePostPraise")
    Observable<MessageTo<Boolean>> deleteNeighborLike(@Body DeletePraiseParam param);

    /**
     * 通过帖子id获取帖子详情
     *
     */
    @POST("api/post/getOnePost")
    Observable<MessageTo<NeighborPostTo>> findPostDetail(@Body PostDetailParam param);

    /**
     * 删除邻居圈发帖
     *
     */
    @POST("api/post/deleteOnePost")
    Observable<MessageTo<Boolean>> delNeighborPost(@Body DeletePostParam param);

    /**
     *删除评论
     */
    @POST("api/postComment/deleteOnePostComment")
    Observable<MessageTo<Boolean>> deleteComment(@Body DeletePostParam param);

    /**
     * 添加邻居圈评价
     *
     */
    @POST("api/postComment/saveOnePostComment")
    Observable<MessageTo<NeighborCommentTo>> addNeighborComment(@Body NeighborCommentParam param);

    /**
     * 获取新的用户的评论
     *
     */

    @POST("api/v1/neighbor/new_comment")
    Observable<MessageTo<List<NeighborCommentTo>>> findNeighborPostUserNewComment(@Body NeighborMessageParam param);

    /**
     * 获取新的用户的点赞
     *
     */

    @POST("api/v1/neighbor/new_like")
    Observable<MessageTo<List<NeighborCommentTo>>> findNeighborPostUserNewLike(@Body NeighborMessageParam param);

}
