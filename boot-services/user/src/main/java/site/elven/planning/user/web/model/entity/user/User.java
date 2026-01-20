package site.elven.planning.user.web.model.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import site.elven.planning.api.common.enums.user.UserStatus;
import site.elven.planning.api.common.enums.user.UserType;
import site.elven.planning.boot.base.web.model.entity.base.BaseEntity;

import java.io.Serial;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "user_info")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Filter(name = "delete_filter", condition = "deleted = :deleted")
public class User extends BaseEntity implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    @Column(name = "code", nullable = false, updatable = false, length = 64, unique = true)
    private String code;
    @NotEmpty(message = "{validation.common.not-empty}")
    @Column(name = "username", length = 64, unique = true)
    private String username;
    @Size(min = 6, max = 128, message = "{validation.common.character.size}")
    @Column(name = "password", length = 128)
    private String password;
    @Column(name = "email", length = 128, unique = true)
    private String email;
    @Column(name = "phone_number", length = 16, unique = true)
    private String phoneNumber;
    @Column(name = "first_name", length = 64)
    private String firstName;
    @Column(name = "last_name", length = 64)
    private String lastName;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "gender")
    private Character gender;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UserType type;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.active;
    @Column(name = "last_active_time")
    private Timestamp lastActiveTime;
    @Column(name = "last_login_time")
    private Timestamp lastLoginTime;
    @Column(name = "last_logout_time")
    private Timestamp lastLogoutTime;
    @Column(name = "locked")
    private boolean locked = false;
    @Column(name = "expire_time")
    private LocalDateTime expireTime;
    @Column(name = "enabled")
    private boolean enabled = true;

    @Override
    public boolean isAccountNonExpired() {
        return expireTime.isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
