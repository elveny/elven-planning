package site.elven.planning.scheduler.nacos;

import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import java.util.List;

@SpringBootTest
public class Test1 {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private NacosDiscoveryClient nacosDiscoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Test
    public void test1() {
        List<String> services = discoveryClient.getServices();
        for (String service : services){
            System.out.println("service::"+ service);
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances){
                System.out.println(instance.getHost() + ":" + instance.getPort());
            }
        }
    }

    @Test
    public void test2() {
        List<String> services = nacosDiscoveryClient.getServices();
        for (String service : services){
            System.out.println("service::"+ service);
            List<ServiceInstance> instances = nacosDiscoveryClient.getInstances(service);
            for (ServiceInstance instance : instances){
                System.out.println(instance.getHost() + ":" + instance.getPort());
            }
        }
    }

    @Test
    public void test3() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("planning-plan");
        System.out.println(serviceInstance.getHost() + ":" + serviceInstance.getPort());
        serviceInstance = loadBalancerClient.choose("planning-plan");
        System.out.println(serviceInstance.getHost() + ":" + serviceInstance.getPort());
        serviceInstance = loadBalancerClient.choose("planning-plan");
        System.out.println(serviceInstance.getHost() + ":" + serviceInstance.getPort());
        serviceInstance = loadBalancerClient.choose("planning-plan");
        System.out.println(serviceInstance.getHost() + ":" + serviceInstance.getPort());
    }
}
