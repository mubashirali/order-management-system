package com.order.command;


import akka.actor.typed.ActorRef;
import akka.pattern.StatusReply;
import com.order.actor.OrderStatus;
import lombok.Data;
import lombok.Getter;

import static com.order.actor.OrderStatus.CLOSED;

@Data
public class CloseOrder implements Command {
    private final String orderId;
    @Getter
    private static final OrderStatus status = CLOSED;
    public final ActorRef<OrderStatus> replyTo;
}