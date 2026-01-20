package site.elven.planning.user.configuration.web.security.manager;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

public interface AuthorizationService {
    /**
     * 判断authentication是否有权限访问指定请求
     *
     * @param authentication 认证信息，可确定当前用户
     * @param context        请求上下文
     * @return 是否可访问
     */
    boolean check(Authentication authentication, RequestAuthorizationContext context);
}
