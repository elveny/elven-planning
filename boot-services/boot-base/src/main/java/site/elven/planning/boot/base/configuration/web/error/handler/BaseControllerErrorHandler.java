package site.elven.planning.boot.base.configuration.web.error.handler;

import org.springframework.beans.factory.annotation.Autowired;
import site.elven.planning.boot.base.common.response.DefaultResponse;
import site.elven.planning.boot.base.manager.message.MessageManager;

import java.util.Map;

public class BaseControllerErrorHandler {
    @Autowired
    private MessageManager messageManager;

    protected DefaultResponse<Map<String, Object>> buildResponse(String exceptionCode, String messageCode, String errorMessage) {
        return DefaultResponse.failure(
                exceptionCode,
                messageManager.getMessage(messageCode, errorMessage),
                null
        );
    }

}
