package com.order.command;


import akka.actor.typed.ActorRef;
import akka.pattern.StatusReply;
import com.order.actor.OrderStatus;
import lombok.Data;
import lombok.Getter;

import static com.order.actor.OrderStatus.PAID;

@Data
public class OrderPayment implements Command {
    @Getter
    private static final OrderStatus status = PAID;
    private final String orderId;
    private final String userId;
    private final double amount;
    public final ActorRef<OrderStatus> replyTo;
}