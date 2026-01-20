package site.elven.planning.user.configuration.web.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import site.elven.common.gson.GsonUtils;
import site.elven.planning.boot.base.common.response.DefaultResponse;
import site.elven.planning.boot.base.manager.message.MessageManager;
import site.elven.planning.user.common.exception.ExceptionCode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 权限不足处理器：
 * 当已认证的用户尝试访问他们没有权限访问的资源时，Spring Security 会调用 DefaultAccessDeniedHandler 中的 handle 方法。
 * 例如，假设你有一个受保护的 URL /admin/**，只有具有 ROLE_ADMIN 角色的用户才能访问。如果一个普通用户尝试访问 /admin/some-resource，则会触发 AccessDeniedHandler。
 */
@Component
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {
    @Autowired
    private MessageManager messageManager;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ExceptionCode exceptionCode = ExceptionCode.FORBIDDEN;

        String message = messageManager.getMessage(exceptionCode.getMessageCode(), requestURI, accessDeniedException.getMessage());

        DefaultResponse<Object> failure = DefaultResponse.failure(exceptionCode.getCode(), message, null);
        response.getWriter().write(GsonUtils.toJson(failure));
    }
}
