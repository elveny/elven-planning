package site.elven.planning.user.web.websocket.test;

import io.micrometer.common.util.StringUtils;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import site.elven.common.gson.GsonUtils;
import site.elven.planning.boot.base.configuration.web.aware.SpringContextUtil;
import site.elven.planning.user.common.constant.WebSocketConstants;
import site.elven.planning.user.configuration.web.websocket.WebSocketConfig;
import site.elven.planning.user.web.holder.UserContextHolder;
import site.elven.planning.user.web.model.entity.user.User;
import site.elven.planning.user.web.service.common.token.TokenService;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/ws/test-count/{userId}", configurator = WebSocketConfig.class)
@Component
@Slf4j
public class TestWebSocketServer {

    private final TokenService tokenService;

    public TestWebSocketServer() {
        this.tokenService = SpringContextUtil.getBean(TokenService.class);
    }

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     * 虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
     */
    private static final Set<TestWebSocketServer> webSockets = new CopyOnWriteArraySet<>();

    /**
     * 用来存在线连接用户信息
     */
    private static final ConcurrentHashMap<Long, Session> sessionPool = new ConcurrentHashMap<>();

    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") Long userId) {
        this.session = session;
        this.userId = userId;
        webSockets.add(this);
        sessionPool.put(userId, session);
        sendOneMessage(userId, "连接成功");
        String userName = getUserName(session);
        log.info("建立与UserID：{}, userName: {} 的连接", userId, userName);
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSockets.remove(this);
        sessionPool.remove(this.userId);
        log.info("关闭与UserID：{} 的连接", userId);
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message) {
        User currentUser = UserContextHolder.getCurrentUser();
        Authentication authentication = (Authentication) session.getUserPrincipal();
        User user = (User) authentication.getPrincipal();
        log.info("接收到UserID：{}的消息{}, currentUser:{}", userId, message, GsonUtils.toJson(user));
        sendOneMessage(userId, "收到消息：" + message);
    }

    /**
     * 发送错误时的处理
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发送到UserID：{}的消息传输失败", userId, error);
    }

    /**
     * 广播消息
     *
     * @param message
     */
    public void sendAllMessage(String message) {
        for (TestWebSocketServer socketServer : webSockets) {
            if (socketServer.session.isOpen()) {
                socketServer.session.getAsyncRemote().sendText(message);
            }
        }
    }

    /**
     * 单人单播消息
     *
     * @param userId
     * @param message
     */
    public void sendOneMessage(Long userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null && session.isOpen()) {
            session.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 多人单播消息
     *
     * @param userIds
     * @param message
     */
    public void sendMoreMessage(Long[] userIds, String message) {
        for (Long userId : userIds) {
            Session session = sessionPool.get(userId);
            if (session != null && session.isOpen()) {
                session.getAsyncRemote().sendText(message);
            }
        }
    }

    // 获取用户名
    public String getUserName(Session session) {
        String token = getHeader(session, WebSocketConstants.WEBSOCKET_PROTOCOL);
        return tokenService.getUsernameFromToken(token);
    }

    public String getHeader(Session session, String headerName) {
        String header = (String) session.getUserProperties().get(headerName);
        if (StringUtils.isBlank(header)) {
            try {
                session.close();
            } catch (IOException e) {
                log.error("session close 失败");
            }
        }
        return header;
    }
}
