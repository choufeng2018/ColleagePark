package com.college.common_libs.domain.door;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by xz on 2016/6/22.
 **/
@Data
public class UserTokenInfoTo implements Serializable{


    /**
     * userSid : 430f4cc2-58c8-475f-b34c-ec1a2f0f87f3
     * deviceToken : BLZX160000048
     * tokenKey : 00000003&#20990101&#MD_BLZX_37FF&#0D4A0149B0D273604937DA0B4447EBB20C8CF9780C73A06608B11F20C9B9B97C&#EBA38306F80E2D10972ABCE74E84B10B&#8CDE52AC37FF
     * otherSid : 000000001
     * otherUserid : 15397141131
     */

    private String userSid;
    private String deviceToken;
    private String tokenKey;
    private String otherSid;
    private String otherUserid;



    @Override
    public String toString() {
        return "UserTokenInfoTo{" +
                "userSid='" + userSid + '\'' +
                ", deviceToken='" + deviceToken + '\'' +
                ", tokenKey='" + tokenKey + '\'' +
                ", otherSid='" + otherSid + '\'' +
                ", otherUserid='" + otherUserid + '\'' +
                '}';
    }
}
