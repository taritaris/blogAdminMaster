package com.taritari.master.enums;

public enum ResultEnum {

    /**
     * 状态
     */
    OK("200", "success"),
    BAD_REQUEST("400", "error"),
    UNAUTHORIZED("401", "forbid"),
    NOT_FOUND("404", "noData"),
    PWD_ERROR("300", "passwordError"),
    EXIT("403", "alreadyExist"),
    INTERNAL_SERVER_ERROR("500", "error"),
    SERVICE_UNAVAILABLE("503", "httpError"),
    ERROR("9999", "noData");

    /**
     * 状态码,长度固定为6位的字符串.
     */
    private String code;

    /**
     * 错误信息.
     */
    private String message;

    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return code + ": " + message;
    }

}
