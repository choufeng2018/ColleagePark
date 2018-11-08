package com.college.common_libs.domain.property;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xz on 2016/6/15.
 **/
@NoArgsConstructor
@Data
public class ServiceRequestParam implements Serializable {


    private String loginUserId;
    private String gardenId;
    private String serviceTypeId;
    private String repairTime;
    private String serviceDesc;
    private String images;
    private int    serviceNum;
    private String userName;
    private String phone;
    private String deliverArea;
    private String serviceClassify;
    private String serviceTypeName;
    private int amountNum;
    private List<WaterDistributionTo>serviceTypeIdList;



//    @"serviceTypeId":firstGoodsModel.serveId,//第一个商品id
//    @"serviceTypeName":firstGoodsModel.name,//第一个商品name
//    @"serviceDesc":self.textView.text,//描述
//    @"serviceNum":@(self.selectGoodsCount),//总数
//    @"amountNum":@(self.selectGoodsCount),//总数
//    @"serviceTypeIdList":subParamsAry//
}

