package site.elven.planning.scheduler.common.exception;

import site.elven.planning.boot.base.common.exception.BaseException;

public class SchedulerException extends BaseException {
    public SchedulerException(String code, String message) {
        super(code, message);
    }
}
