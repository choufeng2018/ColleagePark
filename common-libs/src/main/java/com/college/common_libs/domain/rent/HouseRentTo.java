package com.college.common_libs.domain.rent;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class HouseRentTo implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     *主键
     */
    private Long houseInfoId;

    /**
     *园区sid
     */
    private Long gardenId;

    /**
     *发布类型（0：出售，1：出租，2：托管）
     */
    private Integer publishType;

    /**
     *房屋年代
     */
    private String houseYear;

    /**
     *房型
     */
    private String houseType;

    /**
     *朝向
     */
    private String houseOrientation;

    /**
     *面积
     */
    private Double houseArea;

    /**
     *楼层
     */
    private String houseFloor;

    /**
     *装修程度
     */
    private String houseDecoration;

    /**
     *房源图片
     */
    private String houseImages;

    /**
     *价格
     */
    private String housePrice;

    /**
     *房源描述
     */
    private String houseDesc;

    /**
     *看房联系电话
     */
    private String housePhone;

    /**
     *状态（0：待审核，1：上架，2：申请下架，3：下架）
     */
    private Integer status;

    /**
     *创建用户id
     */
    private Long createdby;

    /**
     *创建用户姓名
     */
    private String createName;

    /**
     *创建时间
     */
    private Date createdOn;

    /**
     *修改用户id
     */
    private Long modifiedby;

    /**
     *修改时间
     */
    private Date modifiedOn;

    /**
     *小区名称
     */
    private String gardenName;

    /**
     *房源区域名称
     */
    private String regionName;

    /**
     *房源区域id
     */
    private Long regionId;

    /**
     *是否置顶：0 否， 1 是
     */
    private Integer houseSticky;

    /**
     *信息来源（0：业主发布，1：南都发布）
     */
    private Integer houseTag;

    /**
     *是否精选
     */
    private Integer houseChosen;

    /**
     *房主电话
     */
    private String houseOwnerPhone;

    /**
     *标题
     */
    private String houseSubject;

    /**
     *位置
     */
    private String housePosition;

    /**
     *联系人
     */
    private String houseContactName;

    private String houseAppPhone;

    /**
     *支付方式(0:付三押一 1:付六押一 2:一年一付)
     */
    private String housePayType;

    private List<OnlineHousePropertyConfigTo> configVos;

    private String houseStructure;

    public String getHouseDetailPrice(){
        return "租金："+housePrice;
    }

    public String getHouseDetailArea(){
        return houseArea+"㎡";
    }

    public String getDetailPay(){
        if (housePayType==null){
            return "暂无";
        }else {
            switch (housePayType) {
                case "0":
                    return "付三押一";
                case "1":
                    return "付六押一";
                case "2":
                    return "一年一付";

            }
            return "暂无";
        }

    }
    public String getHouseDetailDesc(){
        if (!TextUtils.isEmpty(houseDesc))
            return houseDesc;
        return "暂无";
    }

}