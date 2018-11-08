package com.college.common_libs.domain.circle;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by xzz on 2017/9/15.
 **/
@Data
public class TypeTo implements Serializable{
    private String typeName;
    private String imagePath;
    private String typeId;

    public TypeTo(String typeId,String typeName, String imagePath) {
        this.typeName = typeName;
        this.imagePath = imagePath;
        this.typeId=typeId;
    }
}
