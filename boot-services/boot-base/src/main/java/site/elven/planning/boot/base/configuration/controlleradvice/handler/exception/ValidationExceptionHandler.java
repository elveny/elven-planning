package site.elven.planning.boot.base.configuration.controlleradvice.handler.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import site.elven.planning.boot.base.common.exception.ExceptionCode;
import site.elven.planning.boot.base.common.response.DefaultResponse;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
@Order(1)
public class ValidationExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> fieldErrorMap = new HashMap<>();
        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().forEach(
                    fieldError -> {
                        String fieldName = fieldError.getField();
                        String message = messageManager.getMessage(fieldError);
                        fieldErrorMap.put(fieldName, message);
                    }
            );
        }

        ExceptionCode exceptionCode = ExceptionCode.METHOD_ARGUMENT;
        DefaultResponse<?> failure = DefaultResponse.failure(
                exceptionCode.getCode(),
                messageManager.getMessage(exceptionCode.getMessageCode(), fieldErrorMap),
                null
        );
        return ResponseEntity.badRequest().headers(headers()).body(failure);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<DefaultResponse<?>> handleConstraintViolationException(ConstraintViolationException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ex.getConstraintViolations().forEach(
                violation -> {
                    String fieldName = violation.getPropertyPath().toString();
                    errorMessage.append(fieldName).append(";");
                }
        );

        ExceptionCode exceptionCode = ExceptionCode.CONSTRAINT_VIOLATION;
        DefaultResponse<?> failure = DefaultResponse.failure(
                exceptionCode.getCode(),
                messageManager.getMessage(exceptionCode.getMessageCode(), errorMessage),
                null
        );
        return ResponseEntity.ok().headers(headers()).body(failure);
    }
}
