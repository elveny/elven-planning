package site.elven.planning.boot.base.configuration.controlleradvice.handler.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import site.elven.planning.boot.base.common.exception.ExceptionCode;
import site.elven.planning.boot.base.common.response.DefaultResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@Order(1)
public class ValidationExceptionHandler extends BaseExceptionHandler {

    String FIELD_LABEL_FORMAT = "field.%s.label";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, List<String>> fieldErrorMap = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> {
                String fieldName = fieldError.getField();
                String validationMessage = getValidationMessage(fieldError);

                fieldErrorMap.computeIfAbsent(fieldName, k -> new ArrayList<>())
                             .add(validationMessage);
            });
        }

        String errorMessage = fieldErrorMap.isEmpty() ?
            "No validation errors found" :
            mergeErrorMessage(fieldErrorMap);

        ExceptionCode exceptionCode = ExceptionCode.METHOD_ARGUMENT;
        DefaultResponse<?> failure = DefaultResponse.failure(
                exceptionCode.getCode(),
                messageManager.getMessage(exceptionCode.getMessageCode(), errorMessage),
                null
        );

        return ResponseEntity.badRequest().headers(headers()).body(failure);
    }

    /**
     * 获取验证消息（已包含${min}、${max}等替换）
     */
    private String getValidationMessage(FieldError fieldError) {
        // 返回已经由Hibernate Validator处理过的消息
        // 其中的${min}、${max}等已经被替换为具体值
        return fieldError.getDefaultMessage();
    }

    private String getInternationalizedFieldName(String fieldName) {
        try {
            String key = FIELD_LABEL_FORMAT.formatted(fieldName);
            return messageManager.getMessage(key);
        } catch (Exception e) {
            // 如果找不到对应的国际化名称，返回字段名本身
            log.debug("No internationalized name found for field: {}", fieldName, e);
            return fieldName;
        }
    }

    private String mergeErrorMessage(Map<String, List<String>> fieldErrorMap) {
        if (fieldErrorMap == null || fieldErrorMap.isEmpty()) {
            return "";
        }

        return fieldErrorMap.entrySet().stream()
                .map(entry -> {
                    String internationalizedFieldName = getInternationalizedFieldName(entry.getKey());
                    String messages = String.join(", ", entry.getValue());
                    return internationalizedFieldName + ": " + messages;
                })
                .collect(Collectors.joining("; "));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<DefaultResponse<?>> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath().toString())
                .collect(Collectors.joining("; "));

        ExceptionCode exceptionCode = ExceptionCode.CONSTRAINT_VIOLATION;
        DefaultResponse<?> failure = DefaultResponse.failure(
                exceptionCode.getCode(),
                messageManager.getMessage(exceptionCode.getMessageCode(), errorMessage),
                null
        );

        return ResponseEntity.badRequest().headers(headers()).body(failure);
    }

}
