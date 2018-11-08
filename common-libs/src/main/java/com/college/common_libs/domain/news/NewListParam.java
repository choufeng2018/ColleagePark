package com.college.common_libs.domain.news;

/**
 * Created by usb on 2017/9/18.
 */

public class NewListParam {
    private String gardenId;

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    private String noticeType;//      公告类型：1 物业公告， 2 管委会公告， 3 园区风采
    private String limit;
    private int    type;//    // 文章类型：1 新闻公告、2 园区创新、3 法律服务、4 创业服务、5 政策服务、6 投融资
    private int    nextPage;//

    @Override
    public String toString() {
        return "NewListParam{" +
                "gardenId='" + gardenId + '\'' +
                ", noticeType='" + noticeType + '\'' +
                ", type=" + type +
                ", nextPage=" + nextPage +
                '}';
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGardenId() {
        return gardenId;
    }

    public void setGardenId(String gardenId) {
        this.gardenId = gardenId;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

}
