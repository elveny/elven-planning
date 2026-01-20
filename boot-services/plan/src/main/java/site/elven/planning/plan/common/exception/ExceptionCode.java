package site.elven.planning.plan.common.exception;

public enum ExceptionCode {
    PLAN_DEFAULT("6000", "exception.plan.default"),
    PLAN_TEMPLATE_SYSTEM_TEMPLATES_CANNOT_BE_DELETED("6001", "exception.plan.template.system-templates-cannot-be-deleted"),
    PLAN_TASK_IS_NULL("6002", "exception.plan.task.is-null"),
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

    public static ExceptionCode getByCode(String code) {
        for (ExceptionCode value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

}
