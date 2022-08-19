package com.order.dto;

import com.order.actor.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderStateDTO {
    private String orderId;
    private String userId;
    private String basketId;
    private double totalAmount;
    private OrderStatus status;
}
