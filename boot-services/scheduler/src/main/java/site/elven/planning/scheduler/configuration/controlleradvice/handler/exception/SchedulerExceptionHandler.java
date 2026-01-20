package site.elven.planning.scheduler.configuration.controlleradvice.handler.exception;

import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import site.elven.planning.boot.base.common.response.DefaultResponse;
import site.elven.planning.boot.base.configuration.controlleradvice.handler.exception.BaseExceptionHandler;
import site.elven.planning.scheduler.common.exception.ExceptionCode;

@Slf4j
@ControllerAdvice
@Order(1)
public class SchedulerExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(value = {SchedulerException.class})
    public ResponseEntity<DefaultResponse<?>> handleSchedulerException(SchedulerException ex) {
        log.error("Scheduler Exception: {}", ex.getMessage(), ex);

        ExceptionCode exceptionCode = ExceptionCode.SCHEDULER_DEFAULT;
        DefaultResponse<?> failure = DefaultResponse.failure(
                exceptionCode.getCode(),
                messageManager.getMessage(exceptionCode.getMessageCode(), ex.getMessage()),
                null);
        return ResponseEntity.badRequest().headers(headers()).body(failure);
    }

    @ExceptionHandler(value = {site.elven.planning.scheduler.common.exception.SchedulerException .class})
    public ResponseEntity<DefaultResponse<?>> handleSchedulerException(site.elven.planning.scheduler.common.exception.SchedulerException  ex) {
        log.error("Scheduler Exception: {}", ex.getMessage(), ex);

        DefaultResponse<?> failure = DefaultResponse.failure(
                ex.getCode(),
                ex.getMessage(),
                null);
        return ResponseEntity.badRequest().headers(headers()).body(failure);
    }
}
