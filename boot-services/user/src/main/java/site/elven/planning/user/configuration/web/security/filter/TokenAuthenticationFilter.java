package site.elven.planning.user.configuration.web.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import site.elven.planning.user.common.constant.CommonConstants;
import site.elven.planning.user.common.constant.WebSocketConstants;
import site.elven.planning.user.configuration.web.config.ApplicationConfig;
import site.elven.planning.user.web.exception.UserOnlineNotExistsException;
import site.elven.planning.user.web.model.vo.OnlineUser;
import site.elven.planning.user.web.service.common.redis.RedisService;
import site.elven.planning.user.web.service.common.token.TokenService;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private ApplicationConfig applicationConfig;

    /**
     * 加载用户特定数据的核心接口。
     * 它作为用户DAO在整个框架中使用，并且是DaoAuthenticationProvider使用的策略
     * 从ApplicationConfig中创建的Bean对象获取
     */
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisService redisService;

    /**
     * 总体流程：
     * 如果我们有我们的用户电子邮箱并且用户未通过身份验证，我们会从数据库中获取用户详细信息（loadUserByUsername --> UserDetails）
     * 然后我们需要做的是检查用户是否有效，如果用户和令牌有效，我们创建一个UsernamePasswordAuthenticationToken对象，传递UserDetails & 凭证 & 权限信息
     * 扩展上面生成的authToken，包含我们请求的详细信息，然后更新安全上下文中的身份验证令牌
     * 最后一步执行过滤器chain，别忘记放行
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        // 获取请求路径
        String servletPath = request.getServletPath();

        // 如果当前请求路径在白名单中，则直接放行
        ApplicationConfig.Security security = applicationConfig.getSecurity();
        if (Arrays.stream(security.getWhiteList())
                .anyMatch(pattern -> new AntPathMatcher().match(pattern, servletPath))) {
            filterChain.doFilter(request, response);
            return;
        }

        // 从请求头中获取认证信息
        String token = extractTokenFromRequest(request);
        if(token == null){
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        // 从 token 中解析出 username
        String username = tokenService.getUsernameFromToken(token);
        if(username == null){
            filterChain.doFilter(request, response);
            return;
        }

        // 根据username，获取数据库中的用户信息，封装UserDetails对象
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        if (!tokenService.validateToken(token, userDetails)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 从缓存中获取用户信息
        String cachePrefix = applicationConfig.getRedis().getCachePrefix();
        String onlineCacheKey = CommonConstants.REDIS_CACHE_KEY_FORMAT_USER_ONLINE.formatted(cachePrefix, username);
        Object cacheObject = redisService.get(onlineCacheKey);
        if(cacheObject == null){
            throw new UserOnlineNotExistsException(username);
        }
        OnlineUser onlineUser = (OnlineUser) cacheObject;

        // 校验token 与 缓存中的token是否一致
        if(!token.equals(onlineUser.getToken())){
            filterChain.doFilter(request, response);
            return;
        }

        // 创建认证令牌
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                // 用户凭证
                null,
                userDetails.getAuthorities());
        authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
        // 更新安全上下文的持有用户
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }

    private String extractTokenFromRequest(HttpServletRequest request){
        String token = extractTokenFromHttpRequest(request);
        if(token == null){
            token = extractTokenFromWebSocketRequest(request);
        }
        return token;
    }

    private String extractTokenFromHttpRequest(HttpServletRequest request) {
        // 从http请求头中提取 token
        String header = applicationConfig.getToken().getHeader();
        String tokenPrefix = applicationConfig.getToken().getTokenPrefix();
        int length = tokenPrefix.length();
        String bearerToken = request.getHeader(header);
        if (bearerToken != null && bearerToken.startsWith(tokenPrefix)) {
            return bearerToken.substring(length);
        }
        return null;
    }

    private String extractTokenFromWebSocketRequest(HttpServletRequest request) {
        // 从WebSocket请求头中提取 token
        return request.getHeader(WebSocketConstants.WEBSOCKET_PROTOCOL);
    }
}
