package site.elven.planning.plan.common.exception;

import site.elven.planning.boot.base.common.exception.BaseException;

public class PlanException extends BaseException {
    public PlanException(String code, String message) {
        super(code, message);
    }
}
