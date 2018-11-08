package com.college.common_libs.api;

import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.recruit.CompanyRecruitParam;
import com.college.common_libs.domain.recruit.RecruitItemTo;
import com.college.common_libs.domain.recruit.RecruitParam;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by usb on 2017/9/20.
 */

public interface RecruitApi {
    @POST("api/companyRecruit/findCompanyRecruitList")
    Observable<MessageTo<List<RecruitItemTo>>> findRecruitList(@Body RecruitParam param);
    @POST("api/garden/getRecruitInforByParam")
    Observable<MessageTo<List<RecruitItemTo>>> findRecruitListByCompany(@Body CompanyRecruitParam param);
}
