package site.elven.planning.user.common.exception;

public enum ExceptionCode {
    UNAUTHORIZED("0401", "exception.security.unauthorized"),
    FORBIDDEN("0403", "exception.security.forbidden"),
    TOKEN_EXPIRED("0400", "exception.security.token-expired"),
    TOKEN_SIGNATURE("0400", "exception.security.token-signature"),
    USER_ONLINE_NOT_EXISTS("0410", "exception.user.online-not-exists"),
    ;

    private final String code;
    private final String messageCode;

    ExceptionCode(String code, String messageCode) {
        this.code = code;
        this.messageCode = messageCode;
    }

    public String getCode() {
        return code;
    }

    public String getMessageCode() {
        return messageCode;
    }
}
