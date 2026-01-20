package site.elven.planning.user.configuration.controlleradvice.handler.exception;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import site.elven.planning.boot.base.common.response.DefaultResponse;
import site.elven.planning.boot.base.configuration.controlleradvice.handler.exception.BaseExceptionHandler;
import site.elven.planning.user.common.exception.ExceptionCode;

@Slf4j
@ControllerAdvice
@Order(1)
public class SecurityExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<DefaultResponse<?>> handleAuthenticationException(AuthenticationException ex) {
        log.error("", ex);

        ExceptionCode exceptionCode = ExceptionCode.UNAUTHORIZED;
        DefaultResponse<?> failure = DefaultResponse.failure(
                exceptionCode.getCode(),
                messageManager.getMessage(exceptionCode.getMessageCode(), ex.getMessage()),
                null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers()).body(failure);
    }

    @ExceptionHandler(value = {JwtException.class})
    public ResponseEntity<DefaultResponse<?>> handleJwtException(JwtException ex) {
        log.error("", ex);

        ExceptionCode exceptionCode = ExceptionCode.UNAUTHORIZED;
        DefaultResponse<?> failure = DefaultResponse.failure(
                exceptionCode.getCode(),
                messageManager.getMessage(exceptionCode.getMessageCode(), ex.getMessage()),
                null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers()).body(failure);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<DefaultResponse<?>> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        log.error("", ex);

        ExceptionCode exceptionCode = ExceptionCode.FORBIDDEN;
        String requestURI = request.getRequestURI();
        DefaultResponse<?> failure = DefaultResponse.failure(
                exceptionCode.getCode(),
                messageManager.getMessage(exceptionCode.getMessageCode(), requestURI, ex.getMessage()),
                null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).headers(headers()).body(failure);
    }

}
