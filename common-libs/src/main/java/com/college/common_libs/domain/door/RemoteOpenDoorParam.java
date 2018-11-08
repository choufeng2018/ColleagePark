package com.college.common_libs.domain.door;

/**
 * Created by xzz on 2017/11/20.
 */

public class RemoteOpenDoorParam {

    /**
     * requestParam : {"sdkKey":"05A47FFAB5429062A7DBA221DB8A5541582B769EDD379710BF3554A78A280EC450AB1CB84188C5566A1B8A2D2B56D3F67497"}
     * header : {"signature":"5c27d4ec-427b-4698-a32a-1dca4e813574","token":"1509081676726"}
     */

    private RequestParamBean requestParam;
    private HeaderBean header;

    public RequestParamBean getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(RequestParamBean requestParam) {
        this.requestParam = requestParam;
    }

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public static class RequestParamBean {
        /**
         * sdkKey : 05A47FFAB5429062A7DBA221DB8A5541582B769EDD379710BF3554A78A280EC450AB1CB84188C5566A1B8A2D2B56D3F67497
         */

        private String sdkKey;

        public String getSdkKey() {
            return sdkKey;
        }

        public void setSdkKey(String sdkKey) {
            this.sdkKey = sdkKey;
        }
    }

    public static class HeaderBean {
        /**
         * signature : 5c27d4ec-427b-4698-a32a-1dca4e813574
         * token : 1509081676726
         */

        private String signature;
        private String token;

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
