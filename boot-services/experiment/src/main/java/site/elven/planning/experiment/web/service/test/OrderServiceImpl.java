package site.elven.planning.experiment.web.service.test;

import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import site.elven.planning.experiment.proto.CreateOrderRequest;
import site.elven.planning.experiment.proto.CreateOrderResponse;
import site.elven.planning.experiment.proto.OrderItem;
import site.elven.planning.experiment.proto.OrderServiceGrpc;

import java.util.List;

@Service
public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase{

    @Override
    public void createOrder(CreateOrderRequest request, StreamObserver<CreateOrderResponse> responseObserver) {
        String userId = request.getUserId();
        List<OrderItem> itemsList = request.getItemsList();
        System.out.println("Received order request for " + itemsList.size() + " items from user " + userId);
        // 1. 调用库存服务扣减库存（gRPC 客户端调用）
        boolean deducted = true;

        // 2. 构建响应
        CreateOrderResponse response = CreateOrderResponse.newBuilder()
                .setOrderId("ORD-" + System.currentTimeMillis())
                .setStatus(deducted ? "CONFIRMED" : "FAILED")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
