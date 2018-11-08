package com.college.common_libs.domain.recruit;

import java.io.Serializable;

import lombok.Data;

/**
 *  Created by xz on 2017/2/28.
 */
@Data
public class RecruitItemTo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id : 360059405732937728
     * createUserId : 1
     * createTime : 2017-09-20 13:47:32
     * modUserId : 1
     * lastModTime : 2017-09-20 16:25:33
     * gardenId : null
     * title : null
     * job : 12
     * num : 12
     * sex : 男
     * experience : 1年以上
     * education : 大专及以上
     * salary : 11-1111年
     * welfare : ,,
     * address : 12111111111111111111111111111111111111111
     * jobDesc : 21
     * company : 12
     * phone : 21
     * companyDesc : null
     * status : 2
     * reviewTime : null
     * isTop : 1
     * topTime : 2017-09-20 15:17:08
     * senderAccount :
     * senderRoom :
     * hireType : 2
     * otherDesc : 12
     * age : 12
     */

    private String id;//主键
    private String createUserId;//创建人
    private String createTime;//创建时间
    private String modUserId;//修改人
    private String lastModTime;//最后修改时间
    private String gardenId;//园区ID
    private String title;//标题
    private String job;//岗位
    private String    num;//招聘人数
    private String sex;//性别
    private String experience;//工作经验：应届生, 一年以内, 1-3年， 3-5年， 5年以上
    private String education;//学历： 高中以上， 大专以上， 本科以上， 硕士及以上
    private String salary;//工资
    private String welfare;//福利
    private String address;//工作地址
    private String jobDesc;//职位描述
    private String company;//公司名称
    private String phone;//联系电话
    private String companyDesc;//公司简介
    private int    status;//状态: 1 审核中， 2 进行中， 3 已失效， 4 已删除
    private String reviewTime;//审核时间
    private int    isTop;//是否置顶：0 否， 1 是
    private String topTime;//置顶时间
    private String senderAccount;//发布者账号
    private String senderRoom;//发布者房号
    private String hireType;//招聘类型 （1 本公司招聘 2其他招聘）
    private String otherDesc;//其他描述
    private String    age;//年龄
    private String    updateTime;//更新时间
    private String    isNew;//是否是新  0不是新 1是新


}
