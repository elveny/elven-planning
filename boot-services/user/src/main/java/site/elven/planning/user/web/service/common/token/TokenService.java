package site.elven.planning.user.web.service.common.token;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * token服务类
 */
public interface TokenService {
    /**
     * 根据token解析用户名
     *
     * @param token
     * @return
     */
    String getUsernameFromToken(String token);

    /**
     * 校验token与用户是否一致
     *
     * @param token
     * @param userDetails
     * @return
     */
    boolean validateToken(String token, UserDetails userDetails);

    /**
     * 校验token与用户名是否一致
     * @param username
     * @param token
     * @return
     */
    boolean validateToken(String username, String token);

    /**
     * 根据用户信息生成token
     *
     * @param userDetails
     * @return
     */
    String generateToken(UserDetails userDetails);

    /**
     * 刷新token
     *
     * @param oldToken
     * @return
     */
    String refreshHeadToken(String oldToken);
}
