 package com.college.common_libs.domain;

 import java.io.Serializable;

 import lombok.Data;

 @Data

  public class MessageTo<T> implements Serializable
  {
    public static final String DATA_ABORT = "数据异常";
    public static final String PARAMETER_INVALID = "参数异常";
    private static final long serialVersionUID = -7124170122394232970L;
    private int success;
    private String message;
    private String msg;
    private int total;
    private T data;
    private Object tag;

  }
