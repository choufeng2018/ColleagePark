package com.college.common_libs.domain.door;

import java.util.List;

import lombok.Data;

/**
 * Created by xzz on 2017/11/7.
 **/
@Data
public class OpenDoorTo {

    /**
     * openDoorDetailVos : [{"doorId":"1","doorName":"北门","deviceId":"5898658994520064","deviceModel":"A1000","devicePid":"A08EA40826B0E","deviceRegisterId":"10018","supplierId":"1","supplierName":"令令门禁","openDoorCount":null,"lastOpenTime":null},{"doorId":"5824634158907392","doorName":"东门","deviceId":"5897756874964992","deviceModel":"test","devicePid":"test","deviceRegisterId":"75","supplierId":"3","supplierName":"指点","openDoorCount":null,"lastOpenTime":null},{"doorId":"5817525722611712","doorName":"1幢1单元楼门","deviceId":"5812913839341568","deviceModel":"AAA","devicePid":"121212","deviceRegisterId":null,"supplierId":"2","supplierName":"妙兜","openDoorCount":null,"lastOpenTime":null}]
     * operateStr : ["摇一摇","一键开门"]
     * mainOperateStr : null
     */

    private String mainOperateStr;
    private List<OpenDoorListTo> openDoorDetailVos;
    private List<String> operateStr;


    @Override
    public String toString() {
        return "OpenDoorTo{" +
                "mainOperateStr='" + mainOperateStr + '\'' +
                ", openDoorDetailVos=" + openDoorDetailVos +
                ", operateStr=" + operateStr +
                '}';
    }
}
