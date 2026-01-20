package site.elven.planning.user.configuration.web.websocket;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import site.elven.planning.user.common.constant.WebSocketConstants;

import java.io.IOException;

@Order(1)
@Component
@WebFilter(filterName = "WebsocketFilter", urlPatterns = "/ws/**")
public class WebSocketFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = ((HttpServletRequest) servletRequest).getHeader(WebSocketConstants.WEBSOCKET_PROTOCOL);
        // 解决 Sent non-empty 'Sec-WebSocket-Protocol' header but no response was received
        response.setHeader(WebSocketConstants.WEBSOCKET_PROTOCOL, token);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}
