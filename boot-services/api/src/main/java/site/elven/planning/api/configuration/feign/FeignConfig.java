package site.elven.planning.api.configuration.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.codec.Decoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.elven.planning.api.configuration.feign.decoder.ApiResponseEntityDecoder;
import site.elven.planning.api.configuration.feign.decoder.FeignErrorDecoder;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
@EnableFeignClients(basePackages = {"site.elven.planning.api.web.client"})
public class FeignConfig {

    @Bean
    public Decoder apiResponseEntityDecoder(ObjectMapper objectMapper) {
        return new ApiResponseEntityDecoder(objectMapper);
    }

    @Bean
    public FeignErrorDecoder feignErrorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(100, SECONDS.toMillis(1), 3);
    }

}
