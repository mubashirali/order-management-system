package com.fulfillment.service;

import akka.actor.typed.ActorSystem;
import com.fulfillment.actor.DeliverActor;
import com.fulfillment.actor.DeliveryReplyActor;
import com.fulfillment.client.OrderClient;
import com.fulfillment.dto.DeliverStatusResponseDTO;
import com.fulfillment.dto.DeliveryProduct;
import com.fulfillment.dto.FulfillmentRequestDTO;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;

@Singleton
public class ProcessOrderDelivery {

    private final OrderClient client;

    public ProcessOrderDelivery(OrderClient client) {
        this.client = client;
    }

    @Async
    public void deliver(FulfillmentRequestDTO request) {
        ActorSystem<DeliveryProduct> deliveryActor = ActorSystem.create(DeliverActor.create(), "DeliveryActor");
        ActorSystem<DeliverStatusResponseDTO> deliveryReplyActor = ActorSystem.create(DeliveryReplyActor.create(client),
                "DeliveryReplyActor");

        deliveryActor.tell(new DeliveryProduct(request.getOrderId(), request.getUserId(), deliveryReplyActor));
    }
}
