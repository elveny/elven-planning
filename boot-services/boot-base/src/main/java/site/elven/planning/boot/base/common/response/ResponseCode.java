package site.elven.planning.boot.base.common.response;

public enum ResponseCode implements CodeMessageable {
    SUCCESS("0000", "success"),
    ;

    private final String code;
    private final String message;
    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
