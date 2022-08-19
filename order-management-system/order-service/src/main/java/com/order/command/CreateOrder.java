package com.order.command;

import akka.actor.typed.ActorRef;
import akka.pattern.StatusReply;
import com.order.actor.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import static com.order.actor.OrderStatus.CREATED;

@Data
@Builder
public class CreateOrder implements Command {
    private final String orderId;
    private final String basketId;
    private final String userId;
    private final double amount;
    @Getter
    private static final OrderStatus status = CREATED;
    public final ActorRef<OrderStatus> replyTo;
}