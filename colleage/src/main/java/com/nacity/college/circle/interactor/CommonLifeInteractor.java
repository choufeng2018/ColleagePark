package com.nacity.college.circle.interactor;


import com.college.common_libs.domain.circle.DeletePraiseParam;
import com.college.common_libs.domain.circle.NeighborCommentParam;
import com.college.common_libs.domain.circle.NeighborCommentTo;
import com.college.common_libs.domain.circle.NeighborHandleParam;
import com.college.common_libs.domain.circle.NeighborLikeTo;
import com.college.common_libs.domain.circle.NeighborhoodUserConnectParam;
import com.college.common_libs.domain.circle.NeighborhoodUserConnectTo;
import com.nacity.college.circle.BaseCallback;

/**
 * Created by xzz on 2017/7/2.
 **/

public interface CommonLifeInteractor {

    void careOther(NeighborhoodUserConnectParam param, CareOtherCallback callback);

    interface CareOtherCallback extends BaseCallback {

        void careOtherSuccess(NeighborhoodUserConnectTo userConnectTo);
    }

    void cancelCareOther(String ownerSid, String otherSid, CancelCareOtherCallback callback);

    interface CancelCareOtherCallback extends BaseCallback {

        void cancelCareOtherSuccess();
    }

    void addPraise(NeighborHandleParam param, AddPraiseCallback callback);

    void deletePraise(DeletePraiseParam param, AddPraiseCallback callback);

    interface AddPraiseCallback extends BaseCallback {

        void AddPraiseSuccess(NeighborLikeTo likeTo);
    }

    void deletePost(String postSid, DeletePostCallback callback);

    interface DeletePostCallback extends BaseCallback {
        void deletePostSuccess();
    }

    void deleteComment(String commentSid, DeleteCommentCallback callback);

    interface DeleteCommentCallback extends BaseCallback {
        void deleteCommentSuccess();
    }

    void addComment(NeighborCommentParam param, AddCommentCallback callback);

    interface AddCommentCallback extends BaseCallback {
        void addCommentSuccess(NeighborCommentTo commentTo);
    }
}
