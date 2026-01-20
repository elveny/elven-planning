package site.elven.planning.user.web.service.user.impl;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import site.elven.planning.api.common.enums.user.UserType;
import site.elven.planning.user.web.model.entity.user.User;
import site.elven.planning.user.web.service.user.ManageAuthorizationStrategy;

@Component
public class ManageTypeAuthorizationStrategyImpl implements ManageAuthorizationStrategy {
    @Override
    public boolean check(Authentication authentication, RequestAuthorizationContext context) {
        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        User user = (User) authentication.getPrincipal();
        return UserType.admin.equals(user.getType());
    }
}
