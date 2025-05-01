package com.rasitesdmr.order_service.order.service;

import com.rasitesdmr.order_service.domain.enums.OrderStatus;
import com.rasitesdmr.order_service.producer.KafkaProducer;
import event.OrderCreatedEvent;
import event.OrderItemCreatedEvent;
import com.rasitesdmr.order_service.domain.exception.exceptions.NotAcceptableException;
import com.rasitesdmr.order_service.feign.StockFeignClient;
import com.rasitesdmr.order_service.order.Order;
import com.rasitesdmr.order_service.order.model.request.OrderCreateRequest;
import com.rasitesdmr.order_service.order.repository.OrderRepository;
import com.rasitesdmr.order_service.orderitem.OrderItem;
import com.rasitesdmr.order_service.orderitem.model.request.OrderItemCreateRequest;
import com.rasitesdmr.order_service.orderitem.service.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final StockFeignClient stockFeignClient;
    private final KafkaProducer kafkaProducer;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemService orderItemService, StockFeignClient stockFeignClient, KafkaProducer kafkaProducer) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.stockFeignClient = stockFeignClient;
        this.kafkaProducer = kafkaProducer;
    }


    @Override
    public void saveOrder(Order order) {
        try {
            orderRepository.save(order);
        } catch (Exception exception) {
            logger.error("Save order error : {}", exception.getMessage());
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String createOrder(OrderCreateRequest orderCreateRequest) {
        Order order = Order.builder()
                .userId(orderCreateRequest.getUserId())
                .orderStatus(OrderStatus.PENDING.name())
                .build();
        saveOrder(order);

        List<OrderItemCreateRequest> itemsRequest = orderCreateRequest.getItemCreateRequests();
        List<OrderItemCreatedEvent> itemsEvent = new ArrayList<>();

        itemsRequest.forEach(itemRequest -> {
            ResponseEntity<Boolean> isStockSufficient = stockFeignClient.feignIsStockSufficient(itemRequest.getProductId(), itemRequest.getQuantity());
            Boolean result = isStockSufficient.getBody();
            if (Boolean.FALSE.equals(result)) {
                throw new NotAcceptableException("The desired stock is not sufficient");
            }
            OrderItem orderItem = orderItemService.createOrderItem(order, itemRequest);

            OrderItemCreatedEvent orderItemCreatedEvent = OrderItemCreatedEvent.builder()
                    .orderItemId(order.getId())
                    .quantity(orderItem.getQuantity())
                    .totalPrice(orderItem.getTotalPrice())
                    .productId(orderItem.getProductId())
                    .build();
            itemsEvent.add(orderItemCreatedEvent);
        });


        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .orderStatus(order.getOrderStatus())
                .itemEvent(itemsEvent)
                .build();

        kafkaProducer.producerOrderCreatedEvent(orderCreatedEvent);
        return "The order has been successfully created.";
    }

    @Override
    public Boolean updateOrderConfirmationStatus(Long orderId, String orderStatus) {
        try {
            Order order = getById(orderId);
            order.setOrderStatus(orderStatus);
            saveOrder(order);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }


}
