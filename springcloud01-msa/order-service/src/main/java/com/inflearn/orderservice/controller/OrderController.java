package com.inflearn.orderservice.controller;

import com.inflearn.orderservice.dto.OrderDto;
import com.inflearn.orderservice.jpa.OrderEntity;
import com.inflearn.orderservice.messagequeue.KafkaProducer;
import com.inflearn.orderservice.messagequeue.OrderProducer;
import com.inflearn.orderservice.service.OrderService;
import com.inflearn.orderservice.vo.RequestOrder;
import com.inflearn.orderservice.vo.ResponseOrder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order-service")
@Slf4j
public class OrderController {

    OrderService orderService;
    KafkaProducer kafkaProducer;
    OrderProducer orderProducer;

    @Autowired
    public OrderController(OrderService orderService, KafkaProducer kafkaProducer,OrderProducer orderProducer) {
        this.orderService = orderService;
        this.kafkaProducer = kafkaProducer;
        this.orderProducer = orderProducer;
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId, @RequestBody RequestOrder orderDetails){
        log.info("Before added orders data");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        /* JPA */
//        OrderDto orderDto = modelMapper.map(orderDetails, OrderDto.class);
//        orderDto.setUserId(userId);
//        OrderDto createDto = orderService.createOrder(orderDto);
//        ResponseOrder returnValue = modelMapper.map(createDto, ResponseOrder.class);

        OrderDto orderDto = modelMapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);

        /* Kafka */
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());

        /* send this order to the kafka */
        kafkaProducer.send("example-catalog-topic", orderDto);
        orderProducer.send("orders",orderDto);

        ResponseOrder returnValue = modelMapper.map(orderDto, ResponseOrder.class);

        log.info("After added orders data");
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId){
        log.info("Before retrieve orders data");
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });
        log.info("After retrieve orders data");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
