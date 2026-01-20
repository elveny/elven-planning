package site.elven.planning.boot.base.common.response;

public class DefaultResponse<T> extends BaseResponse {
    private T data;

    public DefaultResponse(String code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T value) {
        this.data = value;
    }

    public static <T> DefaultResponse<T> success() {
        return new DefaultResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), null);
    }

    public static <T> DefaultResponse<T> success(T data) {
        return new DefaultResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    public static <T> DefaultResponse<T> failure(String code, String message, T data) {
        return new DefaultResponse<>(code, message, data);
    }

    public static <T> DefaultResponse<T> failure(String code, String message) {
        return failure(code, message, null);
    }
}
