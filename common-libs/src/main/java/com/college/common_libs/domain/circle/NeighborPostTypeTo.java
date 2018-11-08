package com.college.common_libs.domain.circle;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NeighborPostTypeTo implements Serializable {


    private String id;
    private String name;
    private String typeIndex;
    private int sortNo;
    private String picUrl;
    private int autoflag;
    private int status;
}

