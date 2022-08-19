package com.order.actor;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderStatusActor extends AbstractBehavior<OrderStatus> {

    private OrderStatusActor(ActorContext<OrderStatus> context) {
        super(context);
    }

    public static Behavior<OrderStatus> create() {
        return Behaviors.setup(OrderStatusActor::new);
    }

    @Override
    public Receive<OrderStatus> createReceive() {
        return newReceiveBuilder().onMessage(OrderStatus.class, this::onResponse).build();
    }

    private Behavior<OrderStatus> onResponse(OrderStatus statusReply) {
        log.info("Current order Status: {}", statusReply);
        return this;
    }
}