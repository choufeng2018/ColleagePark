package com.college.common_libs.api;

import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.news.EnrollParam;
import com.college.common_libs.domain.news.NewListParam;
import com.college.common_libs.domain.news.NewsDetailParam;
import com.college.common_libs.domain.news.NewsTo;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by usb on 2017/9/18.
 */

public interface NewsApi {
    @POST("api/article/selectModelVoListPage")
    Observable<MessageTo<List<NewsTo>>> findNewsList(@Body NewListParam param);
    /**
     * 活动列表*
     * */
    @POST("api/salon/selectModelVoListPage")
    Observable<MessageTo<List<NewsTo>>> findActivitysList(@Body NewListParam param);
    /**
     * 活动报名*
     * */
    @POST("api/salon/signUpSalon")
    Observable<MessageTo> signUpSalon(@Body EnrollParam param);
    /**
     * 活动详情*
     * */
    @POST("api/salon/selectOne")
    Observable<MessageTo<NewsTo>> OneActivityInfo(@Body EnrollParam param);
    @POST("api/article/selectOne")
    Observable<MessageTo<NewsTo>> findNewsDetail(@Body NewsDetailParam param);
}
