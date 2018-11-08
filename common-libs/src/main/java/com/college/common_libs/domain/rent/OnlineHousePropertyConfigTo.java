package com.college.common_libs.domain.rent;


import java.io.Serializable;

import lombok.Data;

@Data
public class OnlineHousePropertyConfigTo implements Serializable {

    /**
     * 主键
     */
    private Long configId;

    /**
     * 配置类型		10=面积 20=房龄 30=租房面积  31=买房面积  40=房屋类型  50=房屋家电
     */
    private int configType;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 排序号
     */
    private Integer configIndex;

    /**
     * 图标
     */
    private String configIcon;


}