package com.college.common_libs.domain.circle;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xz on 2016/11/9.
 **/
@NoArgsConstructor
@Data
public class NeighborhoodUserConnectTo implements Serializable {


    /**
     * id : 8
     * userId : 19
     * followUserId : true
     */

    private String id;
    private String userId;
    private String followUserId;
}

