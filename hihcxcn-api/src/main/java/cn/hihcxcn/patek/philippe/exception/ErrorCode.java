package cn.hihcxcn.patek.philippe.exception;

/**
 * 错误码集合
 * @author yong
 * @date 2024/02/06
 */
public enum ErrorCode {
    /**
     * 成功
     */
    SUCCESS(200, "SUCCESS"),

    /**
     * 通用错误码
     */
    ERROR(-1,"未知异常"),

    /**
     * 参数校验失败
     */
    PARAMS_INVALID(3,"非法参数"),

    DML_FAILURE(4,"未成功操作数据库"),

    MEMBER_NOT_EXISTS(5, "用户信息不存在"),

    NO_LATEST_DATA(6, "无最新数据"),

    PARAM_LIST_UPPER_LIMIT(7, "入参集合超出限制"),

    DML_FAILURE_REVERSE(8,"未成功操作数据库"),

    /**
     * 调用其他接口失败
     */
    TOP_INTERFACE_FAILED(1001, "API接口异常"),
    RESULT_NO_DATA(1002, "返回结果没有数据");


    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return code;
    }

    public String message() {
        return message;
    }

    public static ErrorCode parse(Integer errorCode){
        for (ErrorCode item : ErrorCode.values()) {
            if (item.code().equals(errorCode)) {
                return item;
            }
        }
        return ErrorCode.ERROR;
    }
}
