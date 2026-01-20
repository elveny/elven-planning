package site.elven.planning.user.configuration.web.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import site.elven.common.gson.GsonUtils;
import site.elven.planning.boot.base.common.response.DefaultResponse;
import site.elven.planning.boot.base.manager.message.MessageManager;
import site.elven.planning.user.common.exception.ExceptionCode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 未授权处理器
 */
@Component
public class UnauthorizedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private MessageManager messageManager;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ExceptionCode exceptionCode = ExceptionCode.UNAUTHORIZED;

        String message = messageManager.getMessage(exceptionCode.getMessageCode(), authException.getMessage());

        DefaultResponse<Object> failure = DefaultResponse.failure(exceptionCode.getCode(), message, null);
        response.getWriter().write(GsonUtils.toJson(failure));
    }
}
