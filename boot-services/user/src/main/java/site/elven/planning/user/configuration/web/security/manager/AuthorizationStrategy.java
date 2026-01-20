package site.elven.planning.user.configuration.web.security.manager;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

public interface AuthorizationStrategy {
    boolean check(Authentication authentication, RequestAuthorizationContext context);
}
