package com.order.actor;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.order.client.PaymentProviderClient;
import com.order.dto.PaymentDTO;
import com.order.event.OrderCreated;
import io.micronaut.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;

//TODO Fix
@Slf4j
public class OrderPaymentActor  {

//    private final PaymentProviderClient client;
//
//    public OrderPaymentActor(ActorContext<OrderCreated> context, PaymentProviderClient client) {
//        super(context);
//        this.client = client;
//    }
//
//
//    public static Behavior<OrderCreated> create(PaymentProviderClient client) {
//        return Behaviors.setup(context -> new OrderPaymentActor(context, client));
//    }
//
//    @Override
//    public Receive<OrderCreated> createReceive() {
////        return newReceiveBuilder().onMessage(DeliverStatusResponseDTO.class, this::onResponse).build();
//        return null;
//    }
//
//    private Behavior<OrderCreated> onResponse(PaymentDTO paymentDTO) {
//        log.info("Responding Status back to OrderService");
//        HttpResponse<Integer> response = client.requestPayment(paymentDTO);
//
//        log.info("Status: {}", response);
//
//        return this;
//    }
}
