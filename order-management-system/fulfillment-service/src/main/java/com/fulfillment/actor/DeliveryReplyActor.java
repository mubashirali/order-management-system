package com.fulfillment.actor;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.fulfillment.client.OrderClient;
import com.fulfillment.dto.DeliverStatusResponseDTO;
import io.micronaut.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeliveryReplyActor extends AbstractBehavior<DeliverStatusResponseDTO> {

    private final OrderClient client;

    private DeliveryReplyActor(ActorContext<DeliverStatusResponseDTO> context, OrderClient client) {
        super(context);
        this.client = client;
    }

    public static Behavior<DeliverStatusResponseDTO> create(OrderClient client) {
        return Behaviors.setup(context -> new DeliveryReplyActor(context, client));
    }

    @Override
    public Receive<DeliverStatusResponseDTO> createReceive() {
        return newReceiveBuilder().onMessage(DeliverStatusResponseDTO.class, this::onResponse).build();
    }

    private Behavior<DeliverStatusResponseDTO> onResponse(DeliverStatusResponseDTO responseDTO) {
        log.info("Responding Status back to OrderService");
        HttpStatus httpStatus = client.deliveryStatus(responseDTO);

        log.info("Status: {}", httpStatus);

        return this;
    }
}
