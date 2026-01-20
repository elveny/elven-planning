package site.elven.planning.user.web.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OnlineUser {
    private String username;
    private String token;
    private String ip;
    private String location;
    private String browser;
    private String os;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private String status;
    private String description;
    private String expireTime;
    private String refreshTime;
}
