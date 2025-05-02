package com.rasitesdmr.order_service.order.model.request;

import com.rasitesdmr.order_service.orderitem.model.request.OrderItemCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {
    private Long userId;
    List<OrderItemCreateRequest> itemCreateRequests;
}
