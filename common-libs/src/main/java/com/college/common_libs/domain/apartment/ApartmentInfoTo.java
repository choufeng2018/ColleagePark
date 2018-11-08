package com.college.common_libs.domain.apartment;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ApartmentInfoTo implements Serializable {

    /**   garden_id **/
    private String gardenId;

    /** 园区编号  garden_no **/
    private String gardenNo;

    /** 园区名称  garden_name **/
    private String gardenName;


    /** 园区图片  garden_image **/
    private String gardenImage;

    /** 园区描述  garden_desc **/
    private String gardenDesc;

    /** 园区电话  garden_tel **/
    private String gardenTel;

    /** 家政电话  domestic_tel **/
    private String domesticTel;

    /** 租售电话  rental_tel **/
    private String rentalTel;

    /** 园区详细地址  garden_address **/
    private String gardenAddress;

    /** 省份id  province_id **/
    private Integer provinceId;

    /** 市id  city_id **/
    private Integer cityId;

    /** 县id  county_id **/
    private Integer countyId;

    private String cityName;

    /** 经度  lng **/
    private String lng;

    /** 纬度  lat **/
    private String lat;

    /** 上班时间  to_work_time **/
    private String toWorkTime;

    /** 下班时间  off_work_time **/
    private String offWorkTime;

    /** 响应超时(单位分钟）  response_overtime **/
    private Integer responseOvertime;

    /** 处理超时(单位分钟)  dispose_overtime **/
    private Integer disposeOvertime;

    /** 是否开放0,未开放  1开放  is_active **/
    private Boolean isActive;

    /** 项目备注  garden_remark **/
    private String gardenRemark;

    /**   create_user_id **/
    private Long createUserId;

    /**   mod_user_id **/
    private Long modUserId;

    /**   create_time **/
    private Date createTime;

    /**   last_mod_time **/
    private Date lastModTime;


}

