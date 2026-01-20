package site.elven.planning.api.web.model.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRsp {
    private boolean checkResult = false;
    private String token;
    private UserContext userContext;
}
