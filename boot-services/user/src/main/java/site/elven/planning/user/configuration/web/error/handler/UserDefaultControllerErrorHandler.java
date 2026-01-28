package site.elven.planning.user.configuration.web.error.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;
import site.elven.planning.boot.base.common.response.DefaultResponse;
import site.elven.planning.boot.base.configuration.web.error.annotation.ControllerError;
import site.elven.planning.boot.base.configuration.web.error.handler.BaseControllerErrorHandler;
import site.elven.planning.user.common.exception.ExceptionCode;
import site.elven.planning.user.web.exception.UserOnlineNotExistsException;

import java.util.Map;

@Component
public class UserDefaultControllerErrorHandler extends BaseControllerErrorHandler {

    @ControllerError(ExpiredJwtException.class)
    public DefaultResponse<Map<String, Object>> handleExpiredJwtException(String errorMessage){
        return buildResponse(ExceptionCode.TOKEN_EXPIRED.getCode(), ExceptionCode.TOKEN_EXPIRED.getMessageCode(), errorMessage);
    }

    @ControllerError(SignatureException.class)
    public DefaultResponse<Map<String, Object>> handleSignatureException(String errorMessage){
        return buildResponse(ExceptionCode.TOKEN_SIGNATURE.getCode(), ExceptionCode.TOKEN_SIGNATURE.getMessageCode(), errorMessage);
    }

    @ControllerError(UserOnlineNotExistsException.class)
    public DefaultResponse<Map<String, Object>> handleUserOnlineNotExistsException(String errorMessage){
        return buildResponse(ExceptionCode.USER_ONLINE_NOT_EXISTS.getCode(), ExceptionCode.USER_ONLINE_NOT_EXISTS.getMessageCode(), errorMessage);
    }

}
