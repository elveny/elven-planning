package site.elven.planning.api.web.model.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import site.elven.planning.api.common.enums.user.UserStatus;
import site.elven.planning.api.common.enums.user.UserType;
import site.elven.planning.api.web.model.base.request.page.PageReq;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserReq extends PageReq {
    @JsonProperty("code")
    private String code;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;
    @JsonProperty("gender")
    private Character gender;
    @JsonProperty("type")
    @Enumerated(EnumType.STRING)
    private UserType type;
    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @JsonProperty("last_active_time")
    private Timestamp lastActiveTime;
    @JsonProperty("last_login_time")
    private Timestamp lastLoginTime;
    @JsonProperty("last_logout_time")
    private Timestamp lastLogoutTime;
    @JsonProperty("locked")
    private boolean locked;
    @JsonProperty("expire_time")
    private LocalDateTime expireTime;
    @JsonProperty("enabled")
    private boolean enabled;
}
