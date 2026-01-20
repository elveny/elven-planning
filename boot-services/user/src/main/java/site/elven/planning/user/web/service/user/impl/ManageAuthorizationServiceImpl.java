package site.elven.planning.user.web.service.user.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Service;
import site.elven.planning.user.configuration.web.security.manager.AuthorizationStrategy;
import site.elven.planning.user.web.service.user.ManageAuthorizationService;

import java.util.function.Supplier;

@Service
public class ManageAuthorizationServiceImpl implements ManageAuthorizationService {

    private final AuthorizationStrategy authorizationStrategy;

    public ManageAuthorizationServiceImpl(AuthorizationStrategy authorizationStrategy) {
        this.authorizationStrategy = authorizationStrategy;
    }

    @Override
    public boolean check(Authentication authentication, RequestAuthorizationContext context) {
        return authorizationStrategy.check(authentication, context);
    }
}
