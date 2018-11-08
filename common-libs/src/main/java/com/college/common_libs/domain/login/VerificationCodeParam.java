package com.college.common_libs.domain.login;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xzz on 2017/9/17.
 **/

@NoArgsConstructor
@Data
public class VerificationCodeParam {

    /**
     * userPhone :
     * validcodeType :
     */

    private String userPhone;
    private String validcodeType;
}
