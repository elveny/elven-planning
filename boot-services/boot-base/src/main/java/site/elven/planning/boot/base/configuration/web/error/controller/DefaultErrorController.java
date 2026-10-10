package site.elven.planning.boot.base.configuration.web.error.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.webmvc.error.ErrorAttributes;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import site.elven.planning.boot.base.common.exception.ExceptionCode;
import site.elven.planning.boot.base.common.response.DefaultResponse;
import site.elven.planning.boot.base.configuration.web.error.handler.adapter.ControllerErrorHandlerAdapter;
import site.elven.planning.boot.base.manager.message.MessageManager;

import java.util.Map;
import java.util.Objects;

@RestController
public class DefaultErrorController implements ErrorController {
    private final ErrorAttributes errorAttributes;
    private final MessageManager messageManager;
    private final ControllerErrorHandlerAdapter controllerErrorHandlerAdapter;

    public DefaultErrorController(ErrorAttributes errorAttributes, MessageManager messageManager, ControllerErrorHandlerAdapter controllerErrorHandlerAdapter) {
        this.errorAttributes = errorAttributes;
        this.messageManager = messageManager;
        this.controllerErrorHandlerAdapter = controllerErrorHandlerAdapter;
    }

    @RequestMapping("/error")
    public DefaultResponse<Map<String, Object>> handleError(HttpServletRequest request, WebRequest webRequest) {
        Map<String, Object> errorAttributesMap = errorAttributes.getErrorAttributes(webRequest,
                ErrorAttributeOptions.of(
                        ErrorAttributeOptions.Include.MESSAGE,
                        ErrorAttributeOptions.Include.STACK_TRACE,
                        ErrorAttributeOptions.Include.EXCEPTION,
                        ErrorAttributeOptions.Include.BINDING_ERRORS,
                        ErrorAttributeOptions.Include.STATUS,
                        ErrorAttributeOptions.Include.ERROR,
                        ErrorAttributeOptions.Include.PATH
                )
        );

        // 获取异常信息：默认 "An unexpected error occurred"
        String message = (String) errorAttributesMap.get("message");
        if (message == null) {
            message = "An unexpected error occurred";
        }

        // 异常类型
        String exception = (String) errorAttributesMap.get("exception");

        // 构造异常响应
        DefaultResponse<Map<String, Object>> exceptionResponse = (DefaultResponse<Map<String, Object>>) controllerErrorHandlerAdapter.handle(exception, message);
        if (exceptionResponse == null){
            // 默认异常码
            ExceptionCode exceptionCode = ExceptionCode.UNEXPECTED_ERROR;
            exceptionResponse = DefaultResponse.failure(
                    exceptionCode.getCode(),
                    messageManager.getMessage(exceptionCode.getMessageCode(), message),
                    null
            );
        }
        else{
            // 设置状态码为 200
            Objects.requireNonNull(((ServletWebRequest) webRequest).getResponse()).setStatus(HttpStatus.OK.value());
        }

        // 如果是 debug 模式，则返回详细的异常信息
        if (extractDebugFromRequest(request)) {
            errorAttributesMap.put("debug", true);
            exceptionResponse.setData(errorAttributesMap);
        }

        return exceptionResponse;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    private boolean extractDebugFromRequest(HttpServletRequest request) {
        // 从请求头中提取 Request-Debug
        String debug = request.getHeader("Request-Debug");
        if(debug != null){
            return Boolean.parseBoolean(debug);
        }
        return false;
    }
}
