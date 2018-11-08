package com.college.common_libs.domain.property;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Created by usb on 2017/10/23.
 */
@NoArgsConstructor
@Data
public class ParkingApplyListTo {
    private String parkId ;//true string 主键ID
    private String  loadArea ;//true string 车位区域
    private String applyStatus ;//true number 状态 1:申请中 2:已处理
    private String applyStatusDesc;// true string 状态描述
    private String createTime;//true string 申请时间
}
