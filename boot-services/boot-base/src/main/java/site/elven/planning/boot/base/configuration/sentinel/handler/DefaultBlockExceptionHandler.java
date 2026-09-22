package site.elven.planning.boot.base.configuration.sentinel.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.AbstractRule;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import tools.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.elven.planning.boot.base.common.exception.ExceptionCode;
import site.elven.planning.boot.base.common.response.DefaultResponse;
import site.elven.planning.boot.base.manager.message.MessageManager;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class DefaultBlockExceptionHandler implements BlockExceptionHandler {
    @Autowired
    private MessageManager messageManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, String resourceName, BlockException blockException) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(429);

        String ruleLimitApp = blockException.getRuleLimitApp();
        String exceptionMessage = blockException.getMessage();
        AbstractRule rule = blockException.getRule();
        String pathInfo = request.getPathInfo();
        String requestURI = request.getRequestURI();
        Map<String, Object> exceptionInfoMap = new HashMap<>();
        exceptionInfoMap.put("ruleLimit-app", ruleLimitApp);
        exceptionInfoMap.put("exception-message", exceptionMessage);
        exceptionInfoMap.put("path-info", pathInfo);
        exceptionInfoMap.put("request-uri", requestURI);
        exceptionInfoMap.put("rule", rule);
        log.error("Sentinel block exception: {}", exceptionInfoMap);

        ExceptionCode exceptionCode = ExceptionCode.SENTINEL_BLOCK_DEFAULT;
        String messageCode = exceptionCode.getMessageCode();
        String message = messageManager.getMessage(messageCode, resourceName);
        DefaultResponse<?> failure = DefaultResponse.failure(exceptionCode.getCode(), message, exceptionInfoMap);

        PrintWriter out = response.getWriter();
        out.print(objectMapper.writeValueAsString(failure));
        out.flush();
        out.close();
    }
}
