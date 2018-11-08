/*     */
package com.college.common_libs.domain.property;
/*     */ 
/*     */

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ServiceMainTo implements Serializable {


    private String serviceId;
    private String serviceTypeId;
    private String serviceTypeName;
    private String gardenId;
    private String serviceUserName;
    private String serviceUserPhone;
    private String serviceAreaDetail;
    private String serviceNo;
    private String serviceDesc;
    private String serviceStatus;
    private String evaluateAutomatic;
    private String evaluateStatus;
    private String evaluateRemark;
    private float evaluateServiceAttitude;
    private float evaluateFinishSpeed;
    private float evaluatePleasedDegree;
    private int seviceNum;
    private double repairUnitPrice;
    private String repairTime;
    private String repairAmount;
    private String dealUserId;
    private String serviceStartTime;
    private String serviceDealTime;
    private String serviceNameDesc;
    private int serviceClassify;
    private Object isDel;
    private Object isValid;
    private String createTime;
    private String lastModTime;
    private String createUserId;
    private String modUserId;
    private String serviceImgs;
    private String serviceStatusDesc;
    private String createTimeDesc;
    private String serviceIdStr;
    private String serviceImg;
    private String operateUserName;
    private String operateUserPhone;
    private String operateDate;
    private String operateDesc;
    private String operateImages;
    private String userJob;
    private String evaluateDesc;
    private String isEvaluate;
    private String serviceContentDesc;
    private double unitPrice;
    private String qtyTitle;
    private String qtyUnit;

    public String getStatusStr() {
        String status = "";

        if ("1".equals(this.serviceStatus))
            status = "等待物业响应";
        else if ("2".equals(this.serviceStatus))
            status = "已派单";
        else if ("22".equals(this.serviceStatus))
            status = "正在帮您解决中";
        else if ("4".equals(this.serviceStatus))
            status = "处理完成";
        else if ("6".equals(this.serviceStatus))
            status = "已结束";
        else if ("9".equals(this.serviceStatus)) {
            status = "已结束";
        }

        return status;
    }

//    public String getEvaluationItem3Str() {
//        if (this.evaluationItem3 == null) {
//            return "差";
//        }
//        if (this.evaluationItem3.intValue() < 3) {
//            return "差";
//        }
//        if (this.evaluationItem3.intValue() == 3) {
//            return "中";
//        }
//        if (this.evaluationItem3.intValue() > 3) {
//            return "好";
//        }
//        return "差";
//    }
}
