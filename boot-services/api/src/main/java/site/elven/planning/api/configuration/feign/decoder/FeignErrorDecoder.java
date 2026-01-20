package site.elven.planning.api.configuration.feign.decoder;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        // 处理HTTP状态码非200的情况
        if (response.status() >= 400) {
            return FeignException.FeignClientException.errorStatus(methodKey, response);
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
