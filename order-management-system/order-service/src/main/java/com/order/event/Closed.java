package com.order.event;

import com.order.actor.OrderStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Closed implements Event {
    private final String orderId;
    private final OrderStatus status;
}

