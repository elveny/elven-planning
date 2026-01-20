package site.elven.planning.user.web.security.method;

import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.stereotype.Component;
import site.elven.planning.api.common.enums.user.UserType;
import site.elven.planning.user.web.holder.UserContextHolder;
import site.elven.planning.user.web.model.entity.user.User;

@Component("auth")
public class AuthorizationLogic {
    public boolean admin(MethodSecurityExpressionOperations operations) {
        // ... authorization logic
        User currentUser = UserContextHolder.getCurrentUser();
        return currentUser != null && currentUser.getType() == UserType.admin;
    }

    public boolean user(MethodSecurityExpressionOperations operations) {
        User currentUser = UserContextHolder.getCurrentUser();
        if(currentUser == null){
            return false;
        }

        if (currentUser.getType() == UserType.admin){
            return true;
        }

        Object returnObject = operations.getReturnObject();
        if (returnObject instanceof User user){
            return user.getCode().equals(currentUser.getCode());
        }

        return false;

    }
}
