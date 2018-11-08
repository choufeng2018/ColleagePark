package com.college.common_libs.domain.news;

/**
 *  Created by usb on 2017/9/18.
 */

public class NewsDetailParam {
    private String id;

    @Override
    public String toString() {
        return "NewsDetailParam{" +
                "id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
