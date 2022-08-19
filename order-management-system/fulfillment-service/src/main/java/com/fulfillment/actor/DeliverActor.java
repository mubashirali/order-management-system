package com.fulfillment.actor;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.fulfillment.dto.DeliverStatusResponseDTO;
import com.fulfillment.dto.DeliveryProduct;
import com.fulfillment.status.DeliverStatus;

public class DeliverActor extends AbstractBehavior<DeliveryProduct> {

    private DeliverActor(ActorContext<DeliveryProduct> context) {
        super(context);
    }

    public static Behavior<DeliveryProduct> create() {
        return Behaviors.setup(DeliverActor::new);
    }

    @Override
    public Receive<DeliveryProduct> createReceive() {
        return newReceiveBuilder().onMessage(DeliveryProduct.class, this::getDeliveryStatus).build();
    }

    private Behavior<DeliveryProduct> getDeliveryStatus(DeliveryProduct cmd) {
        cmd.replyTo.tell(new DeliverStatusResponseDTO(cmd.orderId, cmd.userId, DeliverStatus.getStatus()));
        return this;
    }
}
