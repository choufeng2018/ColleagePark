package com.college.common_libs.domain.circle;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xz on 2017/7/2.
 **/
@NoArgsConstructor
@Data
public class NeighborhoodUserConnectParam implements Serializable {


    /**
     * userId : 0
     * followUserId : 0
     */

    private String userId;
    private String followUserId;
}
