package com.college.common_libs.domain.property;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Created by usb on 2017/10/26.
 */
@NoArgsConstructor
@Data
public class WaterTo implements Serializable {


    /**
     * id : 5819235589292032
     * name : 大华矿泉水
     * content : 大华矿泉水
     * idStr : a5819235589292032
     * pid : 4
     * img : http://picjoy.joyhomenet.com/FuwD_HkdZJgxiQadPnPzO1e4vS-X
     * ispay : 1
     * unitPrice : 30
     * qtyTitle :  瓶
     * qtyDefault : 1
     * qtyUnit : 瓶
     * remark :
     * respTime : null
     * processTime : null
     * serviceClassify : 4
     */

    private String id;
    private String name;
    private String content;
    private String idStr;
    private String pid;
    private String img;
    private int    ispay;
    private String    unitPrice;
    private String qtyTitle;
    private int    qtyDefault;
    private String qtyUnit;
    private String remark;
    private String respTime;
    private String processTime;
    private String serviceClassify;
    private List<WaterTo>  child;

}
