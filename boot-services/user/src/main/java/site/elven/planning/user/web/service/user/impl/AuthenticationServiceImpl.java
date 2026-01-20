package site.elven.planning.user.web.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.elven.planning.api.web.model.user.response.AuthenticationRsp;
import site.elven.planning.api.web.model.user.response.UserContext;
import site.elven.planning.api.web.model.user.response.UserRsp;
import site.elven.planning.user.common.constant.CommonConstants;
import site.elven.planning.user.configuration.web.config.ApplicationConfig;
import site.elven.planning.user.web.model.vo.OnlineUser;
import site.elven.planning.user.web.service.common.redis.RedisService;
import site.elven.planning.user.web.service.common.token.TokenService;
import site.elven.planning.user.web.service.user.AuthenticationService;
import site.elven.planning.user.web.service.user.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Override
    public AuthenticationRsp check(String token) {
        // 从token中解析出username
        String username = tokenService.getUsernameFromToken(token);
        if(username == null){
            return new AuthenticationRsp();
        }

        UserRsp userRsp = userService.findByUsername(username);
        if(userRsp == null){
            return new AuthenticationRsp();
        }

        // 校验token
        boolean result = tokenService.validateToken(token, username);
        if(!result){
            return new AuthenticationRsp();
        }

        // 查看token的online状态
        String cachePrefix = applicationConfig.getRedis().getCachePrefix();
        String onlineCacheKey = CommonConstants.REDIS_CACHE_KEY_FORMAT_USER_ONLINE.formatted(cachePrefix, username);
        Object cacheObject = redisService.get(onlineCacheKey);
        if (cacheObject == null){
            return new AuthenticationRsp();
        }

        // 校验token 与 缓存中的token是否一致
        OnlineUser onlineUser = (OnlineUser) cacheObject;
        if(!token.equals(onlineUser.getToken())){
            return new AuthenticationRsp();
        }

        // 刷新token
        token = tokenService.refreshHeadToken(token);
        return new AuthenticationRsp(result, token, new UserContext(userRsp.getId(), userRsp.getUsername()));
    }
}
