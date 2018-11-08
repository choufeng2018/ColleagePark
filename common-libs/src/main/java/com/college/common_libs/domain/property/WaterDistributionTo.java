package com.college.common_libs.domain.property;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by usb on 2017/10/31.
 */
@NoArgsConstructor
@Data
public class WaterDistributionTo implements Serializable{
    private String serviceTypeId;
    private int waterSeviceNum;
}
