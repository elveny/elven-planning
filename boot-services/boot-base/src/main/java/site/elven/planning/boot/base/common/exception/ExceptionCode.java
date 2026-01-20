package site.elven.planning.boot.base.common.exception;

public enum ExceptionCode {
    METHOD_ARGUMENT("9001", "exception.validation.method-argument"),
    CONSTRAINT_VIOLATION("9002", "exception.validation.constraint-violation"),

    SQL_DEFAULT_EXCEPTION("9100", "exception.sql.default-exception"),
    SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION("9101", "exception.sql.integrity-constraint-violation-exception"),

    SENTINEL_BLOCK_DEFAULT("9200", "exception.sentinel.block.default"),
    UNEXPECTED_ERROR("9500", "exception.unexpected.error"),

    DEFAULT("9998", "exception.default"),
    UNKNOWN("9999", "exception.unknown"),

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
