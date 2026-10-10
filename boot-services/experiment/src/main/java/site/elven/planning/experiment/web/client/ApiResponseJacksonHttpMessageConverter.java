package site.elven.planning.experiment.web.client;

import org.jspecify.annotations.Nullable;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import site.elven.planning.api.common.response.ApiResponse;

import java.io.IOException;
import java.util.Map;

public class ApiResponseJacksonHttpMessageConverter extends JacksonJsonHttpMessageConverter {
    public ApiResponseJacksonHttpMessageConverter() {
        super();
    }

    @Override
    public Object read(ResolvableType type, HttpInputMessage inputMessage, @Nullable Map<String, Object> hints) throws IOException, HttpMessageNotReadableException {
        // 目标类型本身就是 ApiResponse，走父类默认逻辑
        if (ApiResponse.class.isAssignableFrom(type.toClass())) {
            return super.read(type, inputMessage, hints);
        }

        // 构造 ApiResponse<目标类型> 的 ResolvableType
        ResolvableType wrapperType = ResolvableType
                .forClassWithGenerics(ApiResponse.class, type);

        // 用父类逻辑按 ApiResponse<T> 读取
        Object wrapper = super.read(wrapperType, inputMessage, hints);

        // 解包，返回 data
        if (wrapper instanceof ApiResponse<?> apiResponse) {
            return apiResponse.getData();
        }
        return wrapper;
    }

}
