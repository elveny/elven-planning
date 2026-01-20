package site.elven.planning.boot.base.configuration.controlleradvice.handler.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import site.elven.planning.boot.base.common.exception.ExceptionCode;
import site.elven.planning.boot.base.common.response.DefaultResponse;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice
@Order(2)
public class SQLExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<DefaultResponse<?>> handleSQLException(SQLException ex) {
        StringBuilder errorMessage = new StringBuilder();
        int errorCode = ex.getErrorCode();
        String sqlState = ex.getSQLState();
        SQLException nextException = ex.getNextException();
        String message = ex.getMessage();
        log.error("SQL Exception: errorCode={}, sqlState={}, nextException={}, message={}", errorCode, sqlState, nextException, message);

        errorMessage.append("SQL Exception: ")
                .append("errorCode=").append(errorCode)
                .append(", sqlState=").append(sqlState)
                .append(", nextException=").append(nextException)
                .append(", message=").append( message);

        ExceptionCode exceptionCode = ExceptionCode.SQL_DEFAULT_EXCEPTION;
        DefaultResponse<?> failure = DefaultResponse.failure(
                exceptionCode.getCode(),
                messageManager.getMessage(exceptionCode.getMessageCode(), errorMessage),
                null
        );
        return ResponseEntity.ok().headers(headers()).body(failure);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<DefaultResponse<?>> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        int errorCode = ex.getErrorCode();
        String sqlState = ex.getSQLState();
        SQLException nextException = ex.getNextException();
        String message = ex.getMessage();
        log.error("SQL Integrity Constraint Violation Exception: errorCode={}, sqlState={}, nextException={}, message={}", errorCode, sqlState, nextException, message);

        ExceptionCode exceptionCode = ExceptionCode.SQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION;
        DefaultResponse<?> failure = DefaultResponse.failure(
                exceptionCode.getCode(),
                messageManager.getMessage(exceptionCode.getMessageCode()),
                null
        );
        return ResponseEntity.ok().headers(headers()).body(failure);
    }
}
