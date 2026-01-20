package site.elven.planning.boot.base.configuration.controlleradvice.handler.response;

import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import site.elven.planning.boot.base.common.annotation.IgnoreResponseBodyAdvice;
import site.elven.planning.boot.base.common.response.DefaultResponse;

import java.util.Objects;

@Slf4j
@ControllerAdvice
public class DefaultResponseBodyHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 0. 判断是否是 @IgnoreResponseBodyAdvice 注解：是则不处理
        if(returnType.getMethodAnnotation(IgnoreResponseBodyAdvice.class) != null
                // 类被注解了 @IgnoreResponseBodyAdvice
                || returnType.getContainingClass().isAnnotationPresent(IgnoreResponseBodyAdvice.class)
                || AnnotationUtils.findAnnotation(returnType.getContainingClass(), IgnoreResponseBodyAdvice.class) != null){
            return false;
        }

        // 1. 方法返回值是 String：不处理
        Class<?> returnTypeClass = returnType.getParameterType();
        if(String.class.equals(returnTypeClass)){
            return false;
        }

        // 2. 判断是否是管理端接口：不处理
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String path = request.getServletPath();
        if(path.startsWith("/manage")){
            return false;
        }

        // 3. 判断是否是 @ResponseBody 注解：是则处理，否则不处理
        return returnType.getMethodAnnotation(ResponseBody.class) != null
                || returnType.getContainingClass().isAnnotationPresent(ResponseBody.class)
                || AnnotationUtils.findAnnotation(returnType.getContainingClass(), ResponseBody.class) != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, @Nullable MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 如果返回值为 void，则返回成功
        if (returnType.getParameterType().isAssignableFrom(void.class)) {
            return DefaultResponse.success();
        }

        if(body instanceof DefaultResponse){
            return body;
        }
        else {
            return DefaultResponse.success(body);
        }
    }
}
