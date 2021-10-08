package me.jobcollection.modules.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Hongrry
 * @create 2021-10-01 12:49
 */
@Data
@AllArgsConstructor
public class Result {
    /**
     * 是否请求成功
     */
    private boolean success;
    /**
     * 返回状态码
     */
    private int code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private Object data;

    /**
     * 请求成功
     *
     * @param data 数据
     */
    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }

    /**
     * 请求失败
     *
     * @param code 状态码
     * @param msg  返回信息
     */

    public static Result fail(int code, String msg) {
        return new Result(false, code, msg, null);
    }

}
