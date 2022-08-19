package com.order.event;

import com.order.actor.OrderStatus;
import lombok.Data;

@Data
public class OrderCreated implements Event {
    private final String orderId;
    private final String userId;
    private final String basketId;
    private final double totalAmount;
    private final OrderStatus status;
}

