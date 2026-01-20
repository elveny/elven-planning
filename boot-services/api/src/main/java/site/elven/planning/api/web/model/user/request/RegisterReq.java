package site.elven.planning.api.web.model.user.request;

import lombok.Data;
import site.elven.planning.api.common.enums.user.UserType;

import java.sql.Date;

@Data
public class RegisterReq {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Character gender;
    private UserType type;
}
