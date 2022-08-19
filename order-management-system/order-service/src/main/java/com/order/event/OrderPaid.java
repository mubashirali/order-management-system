package com.order.event;


import com.order.actor.OrderStatus;
import lombok.Data;

@Data
public class OrderPaid implements Event {
    private final String orderId;
    private final OrderStatus status;
}