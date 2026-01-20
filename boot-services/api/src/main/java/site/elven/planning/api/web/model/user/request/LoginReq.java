package site.elven.planning.api.web.model.user.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.Data;

@Data
public class LoginReq {
    public interface LoginGroup {}

    @NotEmpty(message = "{validation.common.not-empty}", groups = {LoginGroup.class})
    private String username;
    @Size(min = 6, max = 32, message = "{validation.common.character.size}", groups = {Default.class})
    private String password;

}
