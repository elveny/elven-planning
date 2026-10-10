package site.elven.planning.experiment.configuration.web;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import site.elven.planning.experiment.web.client.ApiResponseJacksonHttpMessageConverter;

@Configuration
public class RestClientConfig {
    @Bean
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder() {
//        return RestClient.builder();
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        httpClientConnectionManager.setMaxTotal(200);
        httpClientConnectionManager.setDefaultMaxPerRoute(50);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(3000))         // 连接超时 3s
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(100)) // 从池取连接超时 100ms
                .setResponseTimeout(Timeout.ofMilliseconds(5000))         // 响应超时 5s
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();

        return RestClient.builder()
                .configureMessageConverters(clientBuilder -> clientBuilder.addCustomConverter(new ApiResponseJacksonHttpMessageConverter()))
                .requestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

    }
}
