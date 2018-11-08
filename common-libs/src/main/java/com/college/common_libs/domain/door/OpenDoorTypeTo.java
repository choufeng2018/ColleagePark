package com.college.common_libs.domain.door;

import java.util.List;

import lombok.Data;

/**
 * Created by xzz on 2017/11/7.
 **/
@Data
public class OpenDoorTypeTo {


    /**
     * openSupplier : [1,2,3]
     * openDevice : true
     */

    private boolean openDevice;
    private List<Integer> openSupplier;


}
