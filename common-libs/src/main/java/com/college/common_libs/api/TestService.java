package com.college.common_libs.api;

import com.college.common_libs.domain.AdInfoTo;
import com.college.common_libs.domain.MessageTo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by xzz on 2017/8/25.
 **/

public interface TestService {

    @GET("api/v1/common/ad/{apartmentSid}/{type}")
    Observable<MessageTo<List<AdInfoTo>>> getAdList(@Path("apartmentSid") String apartmentSid, @Path("type") int type);
}
