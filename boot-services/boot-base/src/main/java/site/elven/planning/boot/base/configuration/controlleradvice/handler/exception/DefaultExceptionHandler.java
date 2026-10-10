package site.elven.planning.boot.base.configuration.controlleradvice.handler.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import site.elven.planning.boot.base.common.exception.DefaultException;
import site.elven.planning.boot.base.common.exception.ExceptionCode;
import site.elven.planning.boot.base.common.response.DefaultResponse;

@Slf4j
@ControllerAdvice
@Order(10)
public class DefaultExceptionHandler extends BaseExceptionHandler{

    // 处理自定义异常
    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<DefaultResponse<?>> handleDefaultException(DefaultException ex) {
        log.error("{}", ex.getMessage(), ex);

        ExceptionCode exceptionCode = ExceptionCode.DEFAULT;
        DefaultResponse<?> failure = DefaultResponse.failure(
                ex.getCode(),
                messageManager.getMessage(exceptionCode.getMessageCode(), ex.getMessage()),
                null
        );
        return ResponseEntity.ok().headers(headers()).body(failure);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultResponse<?>> handleException(Exception ex) {
        log.error("{}", ex.getMessage(), ex);

        ExceptionCode exceptionCode = ExceptionCode.UNKNOWN;
        DefaultResponse<?> failure = DefaultResponse.failure(
                exceptionCode.getCode(),
                messageManager.getMessage(exceptionCode.getMessageCode(), ex.getMessage()),
                null);
        return ResponseEntity.ok().headers(headers()).body(failure);
    }
}
