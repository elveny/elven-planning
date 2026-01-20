package site.elven.planning.user.web.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import site.elven.planning.user.common.constant.CommonConstants;
import site.elven.planning.user.configuration.web.config.ApplicationConfig;
import site.elven.planning.api.web.model.user.request.LoginReq;
import site.elven.planning.api.web.model.user.response.LoginRsp;
import site.elven.planning.user.web.model.vo.OnlineUser;
import site.elven.planning.user.web.service.common.redis.RedisService;
import site.elven.planning.user.web.service.common.token.TokenService;
import site.elven.planning.user.web.service.user.LoginService;

import java.time.LocalDateTime;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private RedisService redisService;

    @Override
    public LoginRsp login(LoginReq loginReq) {
        String username = loginReq.getUsername();
        String password = loginReq.getPassword();
        // 认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 认证通过：获取用户信息
        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();

        // 生成token
        String token = tokenService.generateToken(userDetails);

        // 构建在线用户信息，用于缓存
        OnlineUser onlineUser = new OnlineUser();
        onlineUser.setUsername(username);
        onlineUser.setToken(token);
        onlineUser.setLoginTime(LocalDateTime.now());

        // 构建缓存键
        String cachePrefix = applicationConfig.getRedis().getCachePrefix();
        String onlineCacheKey = CommonConstants.REDIS_CACHE_KEY_FORMAT_USER_ONLINE.formatted(cachePrefix, username);
        redisService.set(onlineCacheKey, onlineUser);

        return new LoginRsp(token);
    }
}
