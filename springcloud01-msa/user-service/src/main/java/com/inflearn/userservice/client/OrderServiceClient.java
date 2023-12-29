package com.inflearn.userservice.client;

import com.inflearn.userservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="order-service") // 호출하는 마이크로서비스 이름
public interface OrderServiceClient {

    @GetMapping("/order-service/{userId}/orders_ng") // 마이크로서비스 URI
    List<ResponseOrder> getOrders(@PathVariable String userId);
}
