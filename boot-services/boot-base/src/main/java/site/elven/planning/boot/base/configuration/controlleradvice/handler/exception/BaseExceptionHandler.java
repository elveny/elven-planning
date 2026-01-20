package site.elven.planning.boot.base.configuration.controlleradvice.handler.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import site.elven.planning.boot.base.manager.message.MessageManager;

public class BaseExceptionHandler {
    @Autowired
    protected MessageManager messageManager;

    protected HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
