package com.order.state;

import com.order.CborSerializable;
import com.order.actor.OrderStatus;
import com.order.dto.OrderStateDTO;
import lombok.Getter;

public class OrderState implements CborSerializable {
    @Getter
    private OrderStateDTO orderStateDTO;

    public OrderState create(OrderStateDTO orderStateDTO) {
        this.orderStateDTO = orderStateDTO;
        return this;
    }

    public OrderState updateStatus(OrderStatus status) {
        orderStateDTO.setStatus(status);
        return this;
    }
}

