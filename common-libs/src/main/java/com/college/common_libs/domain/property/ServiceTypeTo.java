package com.college.common_libs.domain.property;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
 public class ServiceTypeTo implements Serializable {
     private String id;
     private String name;
     private String content;
     private String idStr;
     private String pid;
     private String img;
     private int ispay;
     private double unitPrice;
     private String qtyTitle;
     private int qtyDefault;
     private String qtyUnit;
     private String remark;
     private String serviceClassify;
    private String respTime;
    private String processTime;
    private String changeColor;
    private int click;
    private boolean Seleted;
     private List<ServiceTypeTo> child;

 }
