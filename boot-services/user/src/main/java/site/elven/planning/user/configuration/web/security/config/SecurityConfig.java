package site.elven.planning.user.configuration.web.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import site.elven.planning.user.configuration.web.config.ApplicationConfig;
import site.elven.planning.user.configuration.web.security.filter.TokenAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    // 将自定义JwtAuthenticationFilter注入
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private AuthenticationEntryPoint unauthorizedAuthenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthorizationManager<RequestAuthorizationContext> manageAuthorizationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        req -> {
                            ApplicationConfig.Security security = applicationConfig.getSecurity();
                            // 放行白名单
                            req.requestMatchers(security.getWhiteList()).permitAll();
                            // 管理接口权限校验
                            req.requestMatchers(security.getManageList()).access(manageAuthorizationManager);
                            // 其他请求：普通认证
                            req.anyRequest().authenticated();
                        }

                )
                // 添加JWT过滤器
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(http -> {
                    // 认证失败处理
                    http.authenticationEntryPoint(unauthorizedAuthenticationEntryPoint);
                    // 访问拒绝处理
                    http.accessDeniedHandler(accessDeniedHandler);
                })
                // 防止一个用户多次登录 —— 第二次登录将导致第一次登录无效。
                .sessionManagement(session -> session
                        .maximumSessions(1)
                )
        ;

        return httpSecurity.build();
    }

    /**
     * 认证管理器：基于用户名和密码或使用用户名和密码进行身份验证
     * @param config 认证配置
     * @return 认证管理器
     * @throws Exception 认证异常
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Spring Security 提供的 Spring Data 集成允许在你的查询中引用当前用户。
     * 在查询中包含用户，以支持分页结果，这不仅是有用的，而且是必要的，因为事后过滤结果将无法扩展。
     * @return
     */
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        // 创建一个用户认证提供者
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 是否隐藏”用户不存在异常“（默认为：true-是）
        authProvider.setHideUserNotFoundExceptions(false);
        // 设置用户相信信息，可以从数据库中读取、或者缓存、或者配置文件
        authProvider.setUserDetailsService(userDetailsService);
        // 设置加密机制，若想要尝试对用户进行身份验证，我们需要知道使用的是什么编码
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

}
