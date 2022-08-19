package com.order.command;

import akka.actor.typed.ActorRef;
import akka.pattern.StatusReply;
import com.order.actor.OrderStatus;
import lombok.Data;
import lombok.Getter;

import static com.order.actor.OrderStatus.FULFILLMENT;

@Data
public class FulfillOrder implements Command {
    @Getter
    private static final OrderStatus status = FULFILLMENT;
    private final String orderId;
    public final ActorRef<OrderStatus> replyTo;
}