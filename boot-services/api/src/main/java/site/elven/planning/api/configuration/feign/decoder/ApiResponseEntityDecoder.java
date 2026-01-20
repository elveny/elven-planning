package site.elven.planning.api.configuration.feign.decoder;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import site.elven.planning.api.common.exception.FeignDecodeException;
import site.elven.planning.api.common.response.ApiResponse;

import java.io.IOException;
import java.lang.reflect.Type;

public class ApiResponseEntityDecoder implements Decoder {
    private final ObjectMapper objectMapper;

    public ApiResponseEntityDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        String responseBody = Util.toString(response.body().asReader(Util.UTF_8));
        JavaType parameterType = objectMapper.getTypeFactory().constructType(type);
        JavaType apiResponseType = objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, parameterType);
        ApiResponse<?> apiResponse = objectMapper.readValue(responseBody, apiResponseType);
        if (!apiResponse.getCode().equals("0000")) {
            throw new FeignDecodeException(apiResponse.getCode(), apiResponse.getMessage());
        }

        return apiResponse.getData();
    }
}
