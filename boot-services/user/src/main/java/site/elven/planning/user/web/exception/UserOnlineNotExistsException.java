package site.elven.planning.user.web.exception;

import site.elven.planning.boot.base.common.exception.BaseException;
import site.elven.planning.user.common.exception.ExceptionCode;

public class UserOnlineNotExistsException extends BaseException {
    public UserOnlineNotExistsException(String code, String message) {
        super(code, message);
    }
    public UserOnlineNotExistsException(String message) {
        super(ExceptionCode.USER_ONLINE_NOT_EXISTS.getCode(), message);
    }
}
