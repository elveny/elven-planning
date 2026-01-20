package site.elven.planning.user.configuration.web.websocket;

import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import site.elven.planning.boot.base.configuration.web.aware.SpringContextUtil;
import site.elven.planning.user.common.constant.WebSocketConstants;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfig extends ServerEndpointConfig.Configurator{

    @Autowired
    private SpringContextUtil springContextUtil;

    /**
     * 注入ServerEndpointExporter，
     * 这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // 这个userProperties 可以通过 session.getUserProperties()获取
        final Map<String, Object> userProperties = sec.getUserProperties();

        Map<String, List<String>> headers = request.getHeaders();
        List<String> protocol = headers.get(WebSocketConstants.WEBSOCKET_PROTOCOL);

        // 存放自己想要的header信息
        if(protocol != null){
            userProperties.put(WebSocketConstants.WEBSOCKET_PROTOCOL, protocol.get(0));
        }

    }

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        return super.getEndpointInstance(clazz);
    }
}
