package site.elven.planning.user.web.service.common.token.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import site.elven.planning.user.configuration.web.config.ApplicationConfig;
import site.elven.planning.user.web.service.common.token.TokenService;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Autowired
    private ApplicationConfig applicationConfig;

    @Override
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    @Override
    public boolean validateToken(String username, String token) {
        String tokenUsername = getUsernameFromToken(token);
        return username.equals(tokenUsername) && !isTokenExpired(token);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String username = userDetails.getUsername();
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return createToken(claims);
    }

    @Override
    public String refreshHeadToken(String oldToken) {
        if(StringUtils.isBlank(oldToken)){
            return null;
        }
        //token校验不通过
        Claims claims = getClaimsFromToken(oldToken);

        if(claims == null){
            return null;
        }

        if(isTokenExpired(oldToken)) {
            return null;
        }

        if (tokenRefreshJustBefore(oldToken, 30 * 60)) {
            return oldToken;
        }

        claims.put(CLAIM_KEY_CREATED, new Date());
        return createToken(claims);
    }

    private SecretKey getSignInKey() {
        String secret = applicationConfig.getToken().getSecret();
        byte[] bytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token) {
        SecretKey key = getSignInKey();
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 负责生成JWT的token
     */
    private String createToken(Map<String, Object> claims) {
        SecretKey key = getSignInKey();
        long expire = applicationConfig.getToken().getExpire();
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expire))
                .signWith(key)
                .compact();
    }

    /**
     * 判断token在指定时间内是否刚刚刷新过
     *
     * @param token 原token
     * @param time  指定时间（秒）
     */
    private boolean tokenRefreshJustBefore(String token, int time) {
        Claims claims = getClaimsFromToken(token);
        Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
        Date refreshDate = new Date();
        //刷新时间在创建时间的指定时间内
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(created);
        calendar.add(Calendar.SECOND, time);
        Date timeout = calendar.getTime();

        return refreshDate.after(created) && refreshDate.before(timeout);
    }
}
