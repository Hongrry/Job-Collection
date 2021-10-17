package me.jobcollection.modules.common.utils.enums;

/**
 * @author Hongrry
 * @create 2021-10-01 12:52
 */
public enum StatusCode {
    /**
     * StatusCode
     */
    SUCCESS(101, "成功"),
    FAILURE(102, "失败"),
    USER_NEED_AUTHORITIES(201, "用户未登录"),
    USER_LOGIN_FAILED(202, "用户账号或密码错误"),
    USER_LOGIN_SUCCESS(203, "用户登录成功"),
    USER_NO_ACCESS(204, "用户无权访问"),
    USER_LOGOUT_SUCCESS(205, "用户登出成功"),
    TOKEN_IS_BLACKLIST(206, "此token为黑名单"),
    LOGIN_IS_OVERDUE(207, "登录已失效"),
    /**
     * 验证 状态 10000
     */
    ACCOUNT_PWD_NOT_EXIST(10002, "用户名或密码错误"),

    /**
     * 请求
     */
    BAD_REQUEST(40000, "错误请求"),
    /**
     * 作业
     */
    JOB_SUBMIT_FAILED(20000, "作业提交失败"),

    ACCOUNT_EXIST(10004, "账户已存在"),

    NO_PERMISSION(10005, "无访问权限"),

    SESSION_TIME_OUT(10006, "会话超时"),

    NO_LOGIN(10007, "未登录"),
    ;

    private int code;
    private String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
