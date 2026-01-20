package site.elven.planning.boot.base.configuration.web.error.handler.adapter;

import java.lang.reflect.Method;

public class ControllerErrorHandlerResolver {
    private final Object controllerErrorHandler;
    private final Method method;

    public ControllerErrorHandlerResolver(Object controllerErrorHandler, Method method) {
        this.controllerErrorHandler = controllerErrorHandler;
        this.method = method;
    }

    public Object handle(String errorMessage) {
        try {
            return method.invoke(controllerErrorHandler, errorMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
