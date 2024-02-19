package cn.hihcxcn.patek.philippe.response;

import cn.hihcxcn.patek.philippe.exception.ErrorCode;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 返回结果包装类
 * @author yong
 * @param <T>
 */
@Getter
public class ResultDTO<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 6800103481764638877L;
    public static final Integer SUCCESS_CODE = 0;

    private Integer code;
    
    private String message;
    
    private Boolean success;
    
    private T data;

    public ResultDTO() {
    }

    public ResultDTO(Integer code, T data) {
        this.code = code;
        this.data = data;
        this.success = SUCCESS_CODE.equals(code);
    }

    public ResultDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.success = SUCCESS_CODE.equals(code);
    }

    public ResultDTO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.success = SUCCESS_CODE.equals(code);
        this.data = data;
    }
    
    public static <T> ResultDTO<T> valueOf(Integer code, T data) {
        return new ResultDTO<>(code, data);
    }

    public static <T> ResultDTO<T> valueOf(Integer code, String message) {
        return new ResultDTO<T>(code, message);
    }

    public static <T> ResultDTO<T> valueOf(Integer code, String message, T data) {
        return new ResultDTO<T>(code, message, data);
    }

    public static <T> ResultDTO<T> valueOf(Integer code, String message, String extMessage) {
        return new ResultDTO<>(code, message + " | " + extMessage);
    }

    public static ResultDTO<Void> valueOfOK(String message) {
        return new ResultDTO<>(0, message);
    }

    public static <T> ResultDTO<T> valueOfOK(T data) {
        return new ResultDTO<>(0, data);
    }

    public static <T> ResultDTO<T> valueOfOK(String message, T data) {
        return new ResultDTO<>(0, message, data);
    }

    public static <T> ResultDTO<T> valueOfERROR(String message) {
        return new ResultDTO<>(-1, message);
    }

    public boolean isSuccess() {
        return SUCCESS_CODE.equals(this.code) || ErrorCode.NO_LATEST_DATA.code().equals(this.code);
    }

}
