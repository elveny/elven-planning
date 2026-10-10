package site.elven.planning.experiment.configuration.web;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import site.elven.planning.experiment.web.client.AppTaskRestClient;
import site.elven.planning.experiment.web.client.TaskScheduleRestClient;

@Configuration
public class HttpClientConfig {

    @Bean
    public AppTaskRestClient appTaskRestClient(@LoadBalanced RestClient.Builder builder) {
        return createClient(builder, "http://planning-scheduler/planning-scheduler", AppTaskRestClient.class);
    }

    @Bean
    public TaskScheduleRestClient taskScheduleRestClient(@LoadBalanced RestClient.Builder builder) {
        return createClient(builder, "http://planning-scheduler/planning-scheduler", TaskScheduleRestClient.class);
    }

    private <T> T createClient(RestClient.Builder builder, String baseUrl, Class<T> type) {
        RestClient restClient = builder.baseUrl(baseUrl).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(adapter)
                .build();
        return factory.createClient(type);
    }
}
