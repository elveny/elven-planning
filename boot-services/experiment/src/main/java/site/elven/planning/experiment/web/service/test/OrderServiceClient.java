package site.elven.planning.experiment.web.service.test;

import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Component;
import site.elven.planning.experiment.proto.CreateOrderRequest;
import site.elven.planning.experiment.proto.CreateOrderResponse;
import site.elven.planning.experiment.proto.OrderItem;
import site.elven.planning.experiment.proto.OrderServiceGrpc;
import site.elven.planning.experiment.web.model.rsp.OrderResponse;

import java.util.List;

@Component
public class OrderServiceClient {
    private final OrderServiceGrpc.OrderServiceBlockingStub blockingStub;

    public OrderServiceClient(GrpcChannelFactory channels) {
        this.blockingStub = OrderServiceGrpc.newBlockingStub(
                channels.createChannel("order")
        );
    }

    public OrderResponse createOrder(String userId) {
        List<OrderItem> items = List.of(
                OrderItem.newBuilder().setProductId("item1").setQuantity(1).build(),
                OrderItem.newBuilder().setProductId("item2").setQuantity(2).build()
        );
        CreateOrderRequest request = CreateOrderRequest.newBuilder()
                .setUserId(userId)
                .addAllItems(items)
                .build();
        CreateOrderResponse order = blockingStub.createOrder(request);
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .status(order.getStatus())
                .build();
    }
}
