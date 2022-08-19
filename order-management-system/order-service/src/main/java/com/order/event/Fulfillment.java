package com.order.event;

import com.order.actor.OrderStatus;
import lombok.Data;

@Data
public class Fulfillment implements Event {
    private final String orderId;
    private final OrderStatus status;
}
