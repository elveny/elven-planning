package site.elven.planning.plan.configuration.controlleradvice.handler.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import site.elven.planning.boot.base.common.response.DefaultResponse;
import site.elven.planning.boot.base.configuration.controlleradvice.handler.exception.BaseExceptionHandler;
import site.elven.planning.plan.common.exception.ExceptionCode;
import site.elven.planning.plan.common.exception.PlanException;

@Slf4j
@ControllerAdvice
@Order(3)
public class PlanExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(PlanException.class)
    public ResponseEntity<DefaultResponse<?>> handlePlanException(PlanException ex) {
        log.error("Plan Exception: {}", ex.getMessage());

        String code = site.elven.planning.boot.base.common.exception.ExceptionCode.UNKNOWN.getCode();
        String messageCode = site.elven.planning.boot.base.common.exception.ExceptionCode.UNKNOWN.getMessageCode();

        ExceptionCode exceptionCode = ExceptionCode.getByCode(ex.getCode());
        if (exceptionCode != null){
            code = exceptionCode.getCode();
            messageCode = exceptionCode.getMessageCode();
        }

        DefaultResponse<?> failure = DefaultResponse.failure(
                code,
                messageManager.getMessage(messageCode),
                null
        );
        return ResponseEntity.ok().headers(headers()).body(failure);
    }

}
