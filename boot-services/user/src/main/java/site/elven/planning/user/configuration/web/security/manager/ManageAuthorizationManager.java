package site.elven.planning.user.configuration.web.security.manager;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import site.elven.planning.user.web.service.user.ManageAuthorizationStrategy;

import java.util.function.Supplier;

@Component
public class ManageAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final ManageAuthorizationStrategy authorizationStrategy;

    public ManageAuthorizationManager(ManageAuthorizationStrategy authorizationStrategy) {
        this.authorizationStrategy = authorizationStrategy;
    }

    @Override
    public @Nullable AuthorizationResult authorize(Supplier<? extends @Nullable Authentication> authentication, RequestAuthorizationContext object) {
        return new AuthorizationDecision(authorizationStrategy.check(authentication.get(), object));
    }
}
