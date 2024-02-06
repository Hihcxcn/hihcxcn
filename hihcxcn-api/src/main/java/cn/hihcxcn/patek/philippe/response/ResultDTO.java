package cn.hihcxcn.patek.philippe.response;

import cn.hihcxcn.patek.philippe.exception.ErrorCode;

import java.io.Serializable;

/**
 * 返回结果包装类
 * @author yong
 * @param <T>
 */
public class ResultDTO<T> implements Serializable {
    private String code;
    private String message;
    private Boolean success;
    private T data;
    public static final String SUCCESS_CODE = "0";

    public ResultDTO() {
    }

    public ResultDTO(String code, T data) {
        this.code = code;
        this.data = data;
        this.success = SUCCESS_CODE.equals(code);
    }

    public ResultDTO(String code, String message) {
        this.code = code;
        this.message = message;
        this.success = SUCCESS_CODE.equals(code);
    }

    public ResultDTO(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.success = SUCCESS_CODE.equals(code);
        this.data = data;
    }
    
    public static <T> ResultDTO<T> valueOf(String code, T data) {
        return new ResultDTO<>(code, data);
    }

    public static <T> ResultDTO<T> valueOf(String code, String message) {
        return new ResultDTO<T>(code, message);
    }

    public static <T> ResultDTO<T> valueOf(String code, String message, T data) {
        return new ResultDTO<T>(code, message, data);
    }


    public static <T> ResultDTO<T> valueOf(String code, String message, String extMessage) {
        return new ResultDTO<>(code, message + " | " + extMessage);
    }

    public static ResultDTO<Void> valueOfOK(String message) {
        return new ResultDTO<>("0", message);
    }

    public static <T> ResultDTO<T> valueOfOK(T data) {
        return new ResultDTO<>("0", data);
    }

    public static <T> ResultDTO<T> valueOfOK(String message, T data) {
        return new ResultDTO<>("0", message, data);
    }


    public static <T> ResultDTO<T> valueOfERROR(String message) {
        return new ResultDTO<>("-1", message);
    }

    public boolean isSuccess() {
        if ("0".equals(this.code) || ErrorCode.NO_LATEST_DATA.code().equals(this.code)){
            return true;
        }
        return false;
    }

}
