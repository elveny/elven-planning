package site.elven.planning.scheduler.common.exception;

public enum ExceptionCode {
    SCHEDULER_DEFAULT("7000", "exception.scheduler.default"),
    REQUEST_PARAM_ERROR("7001", "exception.scheduler.request-param-error"),
    APP_NOT_EXIST("7002", "exception.app.registry.not-exist"),

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
