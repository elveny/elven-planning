package site.elven.planning.experiment.web.controller.test;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.elven.planning.experiment.web.model.rsp.OrderResponse;
import site.elven.planning.experiment.web.service.test.OrderServiceClient;

@RestController
@RequestMapping("/web/v1/test/grpc")
public class GRpcTestController {
    private final OrderServiceClient orderServiceClient;

    public GRpcTestController(OrderServiceClient orderServiceClient) {
        this.orderServiceClient = orderServiceClient;
    }

    @RequestMapping("/order/create/{userId}")
    public OrderResponse createOrder(@PathVariable("userId") String userId) {
        return orderServiceClient.createOrder(userId);
    }
}
