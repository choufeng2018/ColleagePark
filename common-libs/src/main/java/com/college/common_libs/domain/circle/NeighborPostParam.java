package com.college.common_libs.domain.circle;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NeighborPostParam implements Serializable {

    /**
     * createUserId : 0
     * content :
     * typeId : 0
     * gardenId : 0
     * urlTitle :
     * postUrl :
     * images :
     */

    private String createUserId;
    private String content;
    private String typeId;
    private String images;
    private String gardenId;
}
